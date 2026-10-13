package de.atlasmc.core.network.permission;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import de.atlasmc.network.permission.PermissionHolder;
import de.atlasmc.permission.Permission;
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
	public boolean setPermission(CharSequence permission, int value) {
		return setPermission(new CorePermission(permission, value));
	}

	@Override
	public boolean setPermission(Permission permission) {
		Objects.requireNonNull(permission, "permission");
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
		if (!permission.equals(perm)) {
			changed = true;
			return true;
		}
		return false;
	}

	@Override
	public boolean removePermission(CharSequence permission) {
		Objects.requireNonNull(permission, "permission");
		Map<?, Permission> map = permissions;
		if (map == null)
			return false;
		Permission perm = map.remove(map);
		if (perm != null) {
			changed = true;
			return true;
		}
		return false;
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
