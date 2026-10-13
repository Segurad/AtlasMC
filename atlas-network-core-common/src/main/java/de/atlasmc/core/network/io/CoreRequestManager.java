package de.atlasmc.core.network.io;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

import de.atlasmc.cache.CacheHolder;
import de.atlasmc.cache.Caching;
import de.atlasmc.io.codec.StreamCodec;
import de.atlasmc.network.AtlasNetwork;
import de.atlasmc.network.NetworkType;
import de.atlasmc.network.io.RequestAction;
import de.atlasmc.network.io.RequestException;
import de.atlasmc.network.io.RequestHandler;
import de.atlasmc.network.io.RequestManager;
import de.atlasmc.network.io.RequestTimeoutException;
import de.atlasmc.network.io.protocol.AbstractPacketRequestType;
import de.atlasmc.network.io.protocol.ServerboundRequestTypeByID;
import de.atlasmc.network.io.protocol.ServerboundRequestTypeByName;
import de.atlasmc.network.io.protocol.ServerboundRequestTypeByUUID;
import de.atlasmc.util.ConcurrentLinkedList;
import de.atlasmc.util.ConcurrentLinkedList.LinkedListIterator;
import de.atlasmc.util.annotation.Nullable;
import de.atlasmc.util.concurrent.future.Future;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

public class CoreRequestManager implements RequestManager, CacheHolder {

	private final Map<NetworkType, RequestHandler<?>> typeHandlers;
	private final Int2ObjectMap<CoreRequestFuture<?>> transactionFutures;
	private final Int2ObjectMap<CoreRequestFuture<?>> hashFuture;
	private final ConcurrentLinkedList<CoreRequestFuture<?>> timeoutQueue;
	private final LinkedListIterator<CoreRequestFuture<?>> timeoutIter;
	private volatile int timeout = Integer.getInteger("de.atlasmc.network.requestmanager.defaultTimeout", 60000);
	private volatile int transactionID = 1;
	
	public CoreRequestManager() {
		this.timeoutQueue = new ConcurrentLinkedList<>();
		this.timeoutIter = timeoutQueue.iterator();
		this.transactionFutures = Int2ObjectMaps.synchronize(new Int2ObjectOpenHashMap<>());
		this.hashFuture = Int2ObjectMaps.synchronize(new Int2ObjectOpenHashMap<>());
		this.typeHandlers = new ConcurrentHashMap<>();
		Caching.register(this);
	}

	@Override
	public int getDefaultTimeout() {
		return timeout;
	}

	@Override
	public void setDefaultTimeout(int timeout) {
		this.timeout = timeout;
	}

	@Override
	public <T> Future<T> request(NetworkType type, String name, RequestAction action, int timeout) {
		return request(ServerboundRequestTypeByName::new, type, name, action, timeout);
	}

	@Override
	public <T> Future<T> request(NetworkType type, UUID uuid, RequestAction action, int timeout) {
		return request(ServerboundRequestTypeByUUID::new, type, uuid, action, timeout);
	}
	
	@Override
	public <T> Future<T> request(NetworkType type, int id, RequestAction action) {
		return request(ServerboundRequestTypeByID::new, type, id, action, timeout);
	}
	
	private synchronized <T, V> Future<T> request(Function<V, ? extends AbstractPacketRequestType> constructor, NetworkType type, V query, RequestAction action, int timeout) {
		var hash = transactionHash(type, query);
		if (action.hasPayload()) {
			var future = this.<T>getFuture(hash, type, query);
			if (future != null) {
				return future;
			}
		}
		var future = this.<T>createFuture(timeout, nextTransaction(), hash, type, query, action);
		try {
			var packet = constructor.apply(query);
			packet.type = type;
			packet.action = action;
			packet.transactionID = nextTransaction();
			AtlasNetwork.getMasterConnection().sendPacket(packet);
		} catch (Exception e) {
			future.complete(null, e);
		}
		return future;
	}
	
	@Nullable
	public <T> CoreRequestFuture<T> getFuture(int hash, NetworkType type, Object query) {
		@SuppressWarnings("unchecked")
		var future = (CoreRequestFuture<T>) hashFuture.get(hash);
		if (future == null)
			return null;
		return future.type == type && future.query == query ? future : null;
	}
	
	private <T> CoreRequestFuture<T> createFuture(int timeout, int transaction, int hash, NetworkType type, Object query, RequestAction action) {
		var future = new CoreRequestFuture<T>(System.currentTimeMillis() + timeout, transaction, hash, type, query);
		transactionFutures.put(transaction, future);
		if (action.hasPayload())
			hashFuture.putIfAbsent(hash, future);
		var iter = timeoutQueue.iterator();
		while (iter.hasNext()) {
			var next = iter.next();
			if (next.timeout >= future.timeout) {
				iter.addBefore(future);
				return future;
			}
		}
		timeoutQueue.add(future);
		return future;
	}
	
	private int transactionHash(NetworkType type, Object query) {
		int result = 1;
		result = 31 * result + type.hashCode();
		result = 31 * result + query.hashCode();
		return result;
	}
	
	private int nextTransaction() {
		return transactionID++;
	}

	@Override
	public void cleanUp() {
		var time = System.currentTimeMillis();
		timeoutIter.reset();
		while (timeoutIter.hasNext()) {
			var next = timeoutIter.next();
			var timeout = next.timeout;
			if (time < timeout)
				break;
			synchronized (next) {
				if (next.isDone())
					continue;
				next.complete(null, new RequestTimeoutException());
				timeoutIter.remove();
			}
		}
		timeoutIter.reset();
	}

	@Override
	public <T> void setHandler(NetworkType type, RequestHandler<T> handler) {
		Objects.requireNonNull(type, "type");
		Objects.requireNonNull(handler, "handler");
		this.typeHandlers.put(type, handler);
	}

	@Override
	public StreamCodec<?> getCodec(NetworkType type) {
		var handler = typeHandlers.get(type);
		return handler != null ? handler.getCodec() : null;
	}

	@Override
	public void handleResponse(int transaction, NetworkType type, Object response) {
		@SuppressWarnings("unchecked")
		var handler = (RequestHandler<Object>) typeHandlers.get(type);
		if (handler == null)
			throw new RequestException("No handler present for type: " + type);
		if (response != null && !handler.getType().isInstance(response))
			throw new RequestException("Handler type " + handler.getType() + " is not assingable from response data: " + response.getClass());
		handler.handle(response);
		if (transaction == 0) {
			handler.handle(response);
			return;
		}
		@SuppressWarnings("unchecked")
		var future = (CoreRequestFuture<Object>) transactionFutures.remove(transaction);
		if (future == null)
			throw new RequestException("Invalid transaction id: " + transaction);
		hashFuture.remove(transaction, future);
		timeoutQueue.remove(future);
		handler.handle(response);
		future.complete(response);
	}

	@Override
	public <T> Future<T> request(NetworkType type, int id, RequestAction action, int timeout) {
		// TODO Auto-generated method stub
		return null;
	}

}
