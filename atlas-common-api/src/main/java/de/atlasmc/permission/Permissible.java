package de.atlasmc.permission;

/**
 * A object that can have {@link Permission}s
 */
public interface Permissible {
	
	/**
	 * Returns whether or not this holder has the given permission
	 * @param permission
	 * @return true if permission is present
	 */
	default boolean hasPermission(CharSequence permission) {
		Permission perm = getPermission(permission);
		return perm != null ? perm.value() != 0 : false;
	}
	
	/**
	 * Returns the permission of this given string.
	 * @param permission
	 * @return permission or null
	 */
	Permission getPermission(CharSequence permission);
	
	/**
	 * Returns the value of the given permission or the given value if not present
	 * @param permission
	 * @param absent
	 * @return value or absent value
	 */
	default int getPermissionValue(CharSequence permission, int absent) {
		Permission perm = getPermission(permission);
		return perm != null ? perm.value() : absent;
	}

}
