package de.atlascore.permission;

import java.util.Collection;
import java.util.UUID;

import de.atlasmc.atlasnetwork.PermissionManager;
import de.atlasmc.cache.Caching;
import de.atlasmc.cache.MapCache;
import de.atlasmc.permission.Permission;
import de.atlasmc.permission.PermissionContext;
import de.atlasmc.permission.PermissionGroup;
import de.atlasmc.permission.PermissionHandler;

public abstract class CoreAbstractPermissionManager implements PermissionManager {
	
	protected final MapCache<String, PermissionGroup> groups;
	protected final MapCache<UUID, PermissionHandler> handlers;
	protected final MapCache<Integer, PermissionContext> contexts;
	
	public CoreAbstractPermissionManager() {
		this.groups = new MapCache<>();
		this.handlers = new MapCache<>();
		this.contexts = new MapCache<>();
		Caching.register(groups);
		Caching.register(contexts);
		Caching.register(handlers);
	}

	@Override
	public Permission createPermission(String permission) {
		return createPermission(permission, 0);
	}

	@Override
	public Permission createPermission(String permission, int value) {
		return new CorePermission(permission, value);
	}

	@Override
	public PermissionContext getContext(int id) {
		return contexts.get(id);
	}

	@Override
	public PermissionGroup getGroup(String name) {
		return groups.get(name);
	}

	@Override
	public PermissionHandler getHandler(UUID uuid) {
		return handlers.get(uuid);
	}

	@Override
	public Collection<PermissionGroup> getGroups() {
		return groups.values();
	}

	@Override
	public Collection<PermissionHandler> getHandlers() {
		return handlers.values();
	}

}
