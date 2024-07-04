package de.atlascore.permission;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import de.atlasmc.permission.Permission;
import de.atlasmc.permission.PermissionGroup;
import de.atlasmc.permission.PermissionGroupHolder;
import de.atlasmc.permission.PermissionHandler;
import de.atlasmc.permission.PermissionHolder;

public class CorePlayerPermissionHandler extends CorePermissionContextHolder implements PermissionHandler {

	private final PermissionGroupHolder groups;
	private final PermissionHolder permissions;
	private final Map<String, String> context;

	public CorePlayerPermissionHandler() {
		groups = new CorePermissionGroupHolder();
		permissions = new CorePermissionHolder();
		context = new ConcurrentHashMap<>();
	}

	@Override
	public Collection<Permission> getPermissions() {
		return permissions.getPermissions();
	}

	@Override
	public void setPermission(String permission, int value) {
		permissions.setPermission(permission, value);
	}

	@Override
	public void setPermission(Permission permission) {
		permissions.setPermission(permission);
	}

	@Override
	public void removePermission(String permission) {
		permissions.removePermission(permission);
	}

	@Override
	public void removePermission(Permission permission) {
		permissions.removePermission(permission);
	}

	@Override
	public Permission getPermission(String permission, boolean allowWildcards) {
		Permission perm = permissions.getPermission(permission, allowWildcards);
		if (perm != null)
			return perm;
		
		return null;
	}

	@Override
	public Map<String, String> getContext() {
		return context;
	}

	@Override
	public void setContext(String key, String context) {
		this.context.put(key, context);
	}

	@Override
	public void removeContext(String key, String context) {
		this.context.remove(key, context);
	}

	@Override
	public String getContext(String key) {
		String context = this.getContext(key);
		if (context != null)
			return context;
		for (PermissionGroup group : groups.getGroups()) {
			context = group.getContext(key);
			if (context != null)
				return context;
		}
		return null;
	}

	@Override
	public Collection<PermissionGroup> getGroups() {
		return this.groups.getGroups();
	}

	@Override
	public PermissionGroup getHighestGroup() {
		return this.groups.getHighestGroup();
	}

	@Override
	public void addPermissionGroup(PermissionGroup group) {
		this.groups.addPermissionGroup(group);
	}

	@Override
	public void removePermissionGroup(PermissionGroup group) {
		this.groups.removePermissionGroup(group);
	}

}
