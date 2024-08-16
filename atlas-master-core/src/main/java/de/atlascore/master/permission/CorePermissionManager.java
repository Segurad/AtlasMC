package de.atlascore.master.permission;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import de.atlasmc.atlasnetwork.AtlasPlayer;
import de.atlasmc.cache.CacheHolder;
import de.atlasmc.cache.Caching;
import de.atlasmc.master.PermissionManager;
import de.atlasmc.permission.PermissionGroup;
import de.atlasmc.permission.PermissionHandler;

public abstract class CorePermissionManager implements PermissionManager, CacheHolder {
	
	protected final Map<String, Ref<PermissionGroup>> groups;
	protected final Map<UUID, Ref<PermissionHandler>> handlers;
	
	/**
	 * Used for loading handlers
	 */
	protected final Object handlersLock = new Object();
	
	/**
	 * Used for loading groups
	 */
	protected final Object groupsLock = new Object();
	
	private final ReferenceQueue<Object> queue;
	
	public CorePermissionManager() {
		this.queue = new ReferenceQueue<>();
		this.handlers = new ConcurrentHashMap<>();
		this.groups = new ConcurrentHashMap<>();
		Caching.register(this);
	}
	
	@Override
	public PermissionHandler getHandler(AtlasPlayer player) {
		final UUID uuid = player.getInternalUUID();
		PermissionHandler handler = internalGetHandler(uuid);
		if (handler != null)
			return handler;
		synchronized (handlersLock) {
			handler = internalGetHandler(uuid);
			if (handler != null)
				return handler;
			handler = loadHandler(player);
			this.handlers.put(uuid, new HandlerRef(uuid, handler, this));
			return handler;
		}
	}
	
	private PermissionHandler internalGetHandler(UUID uuid) {
		Ref<PermissionHandler> ref = handlers.get(uuid);
		if (ref == null)
			return null;
		return ref.get();
	}
	
	@Override
	public List<PermissionGroup> getGroups(String... names) {
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
			return groups;
		synchronized (this.groups) {
			for (int i = 0; i < names.length; i++) {
				String name = names[i];
				if (name == null)
					continue;
				PermissionGroup group = getGroup(name, true);
				if (group == null)
					continue;
				groups.add(group);
			}
			return groups;
		}
	}
	
	@Override
	public PermissionGroup getGroup(String name) {
		return getGroup(name, true);
	}
	
	@Override
	public PermissionGroup getGroup(String name, boolean load) {
		PermissionGroup handler = internalGetGroup(name);
		if (handler != null)
			return handler;
		synchronized (groupsLock) {
			handler = internalGetGroup(name);
			if (handler != null)
				return handler;
			PermissionGroup group = loadGroup(name);
			groups.put(group.getName(), new GroupRef(group, this));
			return group;	
		}
	}
	
	private PermissionGroup internalGetGroup(String name) {
		Ref<PermissionGroup> ref = groups.get(name);
		if (ref == null)
			return null;
		return ref.get();
	}
	
	protected abstract PermissionHandler loadHandler(AtlasPlayer player);
	
	protected abstract PermissionGroup loadGroup(String name);
	
	static abstract class Ref<T> extends WeakReference<T> {

		public Ref(T referent, CorePermissionManager provider) {
			super(referent, provider.queue);
		}
		
		protected abstract void removeRef(CorePermissionManager provider);
		
	}
	
	protected static final class HandlerRef extends Ref<PermissionHandler> {

		private final UUID uuid;
		
		public HandlerRef(UUID uuid, PermissionHandler referent, CorePermissionManager provider) {
			super(referent, provider);
			this.uuid = uuid;
		}

		@Override
		protected void removeRef(CorePermissionManager provider) {
			provider.handlers.remove(uuid, this);
		}
		
	}
	
	protected static final class GroupRef extends Ref<PermissionGroup> {

		private final String name;
		
		public GroupRef(PermissionGroup referent, CorePermissionManager provider) {
			super(referent, provider);
			this.name = referent.getName();
		}

		@Override
		protected void removeRef(CorePermissionManager provider) {
			provider.groups.remove(name, this);
		}
		
	}

	@Override
	public void cleanUp() {
		Ref<?> ref = null;
		while ((ref = (Ref<?>) queue.poll()) != null) {
			ref.removeRef(this);
		}
	}

}
