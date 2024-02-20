package de.atlascore.atlasnetwork;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import de.atlasmc.atlasnetwork.AtlasPlayer;
import de.atlasmc.atlasnetwork.PermissionProvider;
import de.atlasmc.permission.PermissionGroup;
import de.atlasmc.permission.PermissionHandler;
import de.atlasmc.util.concurrent.future.CompletableFuture;
import de.atlasmc.util.concurrent.future.CompleteFuture;
import de.atlasmc.util.concurrent.future.Future;
import de.atlasmc.util.concurrent.future.FutureListener;

public abstract class CorePermissionProvider implements PermissionProvider {
	
	private final Map<String, RefListener<PermissionGroup>> groups;
	private final Map<UUID, RefListener<PermissionHandler>> handlers;
	private final Map<String, Future<PermissionGroup>> futureGroups;
	private final Map<UUID, Future<PermissionHandler>> futureHandlers;
	
	private final ReferenceQueue<Object> queue;
	
	public CorePermissionProvider() {
		this.queue = new ReferenceQueue<>();
		this.handlers = new ConcurrentHashMap<>();
		this.groups = new ConcurrentHashMap<>();
		this.futureGroups = new ConcurrentHashMap<>();
		this.futureHandlers = new ConcurrentHashMap<>();
	}
	
	@Override
	public Future<PermissionHandler> getHandler(AtlasPlayer player) {
		final UUID uuid = player.getInternalUUID();
		PermissionHandler handler = internalGetHandler(uuid);
		if (handler != null)
			return new CompleteFuture<>(handler);
		synchronized (handlers) {
			handler = internalGetHandler(uuid);
			if (handler != null)
				return new CompleteFuture<>(handler);
			Future<PermissionHandler> existingFuture = futureHandlers.get(uuid);
			if (existingFuture != null)
				return existingFuture;
			final CompletableFuture<PermissionHandler> future = new CompletableFuture<>();
			futureHandlers.put(uuid, future);
			loadHandler(player).setListener((result) -> {
				PermissionHandler phandler = (PermissionHandler) result.getNow();
				if (phandler != null)
					this.handlers.put(uuid, new HandlerRefListener(uuid, phandler, this));
				futureHandlers.remove(uuid);
				future.finish(phandler, result.cause());
			});
			return future;
		}
	}
	
	private PermissionHandler internalGetHandler(UUID uuid) {
		RefListener<PermissionHandler> ref = handlers.get(uuid);
		if (ref != null && !ref.refersTo(null)) {
			return ref.get();
		}
		return null;
	}
	
	@Override
	public Future<List<PermissionGroup>> getGroups(String... names) {
		int fetched = 0;
		final int length = names.length;
		List<PermissionGroup> groups = new ArrayList<>(length);
		for (int i = 0; i < length; i++) {
			PermissionGroup group = internalGetGroup(names[i]);
			if (group != null) {
				groups.add(group);
				names[i] = null;
				fetched++;
			}
		}
		if (fetched == length)
			return new CompleteFuture<>(groups);
		synchronized (this.groups) {
			for (int i = 0; i < length; i++) {
				if (names[i] == null)
					continue;
				PermissionGroup group = internalGetGroup(names[i]);
				if (group != null) {
					groups.add(group);
					names[i] = null;
					fetched++;
				}
			}
			if (fetched == length)
				return new CompleteFuture<>(groups);
			final CompletableFuture<List<PermissionGroup>> future = new CompletableFuture<>();
			AtomicInteger total = new AtomicInteger(fetched);
			FutureListener<PermissionGroup> listener = (result) -> {
				synchronized (groups) {
					PermissionGroup group = result.getNow();
					if (group != null)
						groups.add(group);
					if (total.incrementAndGet() == length)
						future.finish(groups);
				}
			};
			for (int i = 0; i < names.length; i++) {
				if (names[i] != null)
					getGroup(names[i], true).setListener(listener);
			}
			return future;
		}
	}
	
	@Override
	public Future<PermissionGroup> getGroup(String name) {
		return getGroup(name, true);
	}
	
	@Override
	public Future<PermissionGroup> getGroup(String name, boolean load) {
		PermissionGroup handler = internalGetGroup(name);
		if (handler != null)
			return new CompleteFuture<>(handler);
		synchronized (groups) {
			handler = internalGetGroup(name);
			if (handler != null)
				return new CompleteFuture<>(handler);
			Future<PermissionGroup> existingFuture = futureGroups.get(name);
			if (existingFuture != null)
				return existingFuture;
			final CompletableFuture<PermissionGroup> future = new CompletableFuture<>();
			futureGroups.put(name, future);
			loadGroup(name).setListener((result) -> {
				PermissionGroup group = (PermissionGroup) result.getNow();
				if (group != null)
					this.groups.put(name, new GroupRefListener(group, this));
				futureGroups.remove(name);
				future.finish(group, result.cause());
			});
			return future;	
		}
	}
	
	private PermissionGroup internalGetGroup(String name) {
		RefListener<PermissionGroup> ref = groups.get(name);
		if (ref != null && !ref.refersTo(null)) {
			return ref.get();
		}
		return null;
	}
	
	protected abstract Future<PermissionHandler> loadHandler(AtlasPlayer player);
	
	protected abstract Future<PermissionGroup> loadGroup(String name);
	
	static abstract class RefListener<T> extends WeakReference<T> {

		public RefListener(T referent, CorePermissionProvider provider) {
			super(referent, provider.queue);
		}
		
		protected abstract void removeRef(CorePermissionProvider provider);
		
	}
	
	private static final class HandlerRefListener extends RefListener<PermissionHandler> {

		private final UUID uuid;
		
		public HandlerRefListener(UUID uuid, PermissionHandler referent, CorePermissionProvider provider) {
			super(referent, provider);
			this.uuid = uuid;
		}

		@Override
		protected void removeRef(CorePermissionProvider provider) {
			provider.handlers.remove(uuid, this);
		}
		
	}
	
	private static final class GroupRefListener extends RefListener<PermissionGroup> {

		private final String name;
		
		public GroupRefListener(PermissionGroup referent, CorePermissionProvider provider) {
			super(referent, provider);
			this.name = referent.getName();
		}

		@Override
		protected void removeRef(CorePermissionProvider provider) {
			provider.groups.remove(name, this);
		}
		
	}

	public void expungeStaleEntries() {
		RefListener<?> ref = null;
		while ((ref = (RefListener<?>) queue.poll()) != null) {
			ref.removeRef(this);
		}
	}

}
