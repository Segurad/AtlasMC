package de.atlascore.permission;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import de.atlasmc.permission.Permission;
import de.atlasmc.permission.PermissionHolder;
import de.atlasmc.util.map.key.CharKey;

public class CorePermissionHolder implements PermissionHolder {
	
	private Map<CharKey, Permission> permissions;
	private volatile boolean changed;

	Map<CharKey, Permission> permissions() {
		return permissions;
	}
	
	@Override
	public Collection<Permission> getPermissions() {
		Map<?, Permission> map = permissions;
		if (map == null)
			return List.of();
		return map.values();
	}

	@Override
	public Permission getPermission(CharSequence permission) {
		Map<?, Permission> map = permissions;
		if (map == null)
			return null;
		Permission perm = map.get(permission);
		if (perm != null)
			return perm;
		return null;
	}

	@Override
	public void setPermission(String permission, int value) {
		setPermission(new CorePermission(permission, value));
	}

	@Override
	public void setPermission(Permission permission) {
		Map<CharKey, Permission> map = permissions;
		if (map == null) {
			synchronized (this) {
				map = permissions;
				if (map == null) {
					map = new ConcurrentHashMap<>();
					permissions = map;
				}
			}
		}
		Permission perm = map.put(CharKey.of(permission.permission()), permission);
		if (!permission.equals(perm))
			changed = true;
	}

	@Override
	public void removePermission(String permission) {
		Map<?, Permission> map = permissions;
		if (map == null)
			return;
		Permission perm = map.remove(map);
		if (perm != null)
			changed = true;
	}

	@Override
	public void removePermission(Permission permission) {
		removePermission(permission.permission());
	}
	
	@Override
	public boolean hasChangedPermissions() {
		return changed;
	}

	@Override
	public void changedPermissions() {
		changed = false;
	}

}
