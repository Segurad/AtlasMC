package de.atlasmc.core.network.server;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

import de.atlasmc.cache.Caching;
import de.atlasmc.cache.MapCache;
import de.atlasmc.network.AtlasNetwork;
import de.atlasmc.network.NetworkType;
import de.atlasmc.network.io.protocol.ServerboundRequestTypeByName;
import de.atlasmc.network.io.protocol.ServerboundRequestTypeByUUID;
import de.atlasmc.network.server.BaseServer;
import de.atlasmc.network.server.ServerGroup;
import de.atlasmc.network.server.ServerManager;
import de.atlasmc.util.concurrent.future.CompletableFuture;
import de.atlasmc.util.concurrent.future.CompleteFuture;
import de.atlasmc.util.concurrent.future.Future;

/**
 * Wrapper for master server manager
 */
public class CoreServerManager implements ServerManager {
	
	private static final Function<String, CompletableFuture<ServerGroup>> loadGroup = CoreServerManager::internalLoadGroup;
	private static final Function<UUID, CompletableFuture<BaseServer>> loadServer = CoreServerManager::internalLoadServer;
	
	private final MapCache<String, ServerGroup> groups;
	private final MapCache<UUID, BaseServer> server;

	private final ConcurrentHashMap<String, CompletableFuture<ServerGroup>> futureGroups;
	private final ConcurrentHashMap<UUID, CompletableFuture<BaseServer>> futureServers;
	
	private volatile ServerGroup fallback;
	
	public CoreServerManager() {
		this.groups = new MapCache<>();
		this.server = new MapCache<>();
		this.futureGroups = new ConcurrentHashMap<>();
		this.futureServers = new ConcurrentHashMap<>();
		Caching.register(groups);
		Caching.register(server);
	}
	
	@Override
	public ServerGroup getFallBack() {
		return fallback;
	}
	
	@Override
	public BaseServer getServer(UUID uuid) {
		return server.get(uuid);
	}
	
	@Override
	public ServerGroup getServerGroup(String name) {
		return groups.get(name);
	}

	@Override
	public Future<ServerGroup> loadServerGroup(String name) {
		return getOrLoad(name, groups, futureGroups, loadGroup);
	}

	@Override
	public Future<BaseServer> loadServer(UUID uuid) {
		return getOrLoad(uuid, server, futureServers, loadServer);
	}
	
	private synchronized <T, K> Future<T> getOrLoad(K key, Map<K, T> cache, Map<K, CompletableFuture<T>> futures, Function<K, CompletableFuture<T>> loader) {
		Objects.requireNonNull(key);
		var val = cache.get(futures);
		if (val != null)
			return CompleteFuture.of(val);
		var future = futures.get(key);
		if (future != null)
			return future;
		future = loader.apply(key);
		futures.put(key, future);
		future.setListener((f) -> {
			synchronized (CoreServerManager.this) {
				futures.remove(key, f);
				if (!f.isSuccess())
					return;
				var result = f.resultNow();
				if (result != null)
					cache.put(key, val);
			}
		});
		return future;
	}
	
	private static CompletableFuture<ServerGroup> internalLoadGroup(String name) {
		var con = AtlasNetwork.getMasterConnection();
		var packet = new ServerboundRequestTypeByName();
		packet.name = name;
		packet.type = NetworkType.SERVER_GROUP.get();
		con.sendPacket(packet);
		return new CompletableFuture<>();
	}
	
	private static CompletableFuture<BaseServer> internalLoadServer(UUID uuid) {
		var con = AtlasNetwork.getMasterConnection();
		var packet = new ServerboundRequestTypeByUUID();
		packet.uuid = uuid;
		packet.type = NetworkType.SERVER.get();
		con.sendPacket(packet);
		return new CompletableFuture<>();
	}

}
