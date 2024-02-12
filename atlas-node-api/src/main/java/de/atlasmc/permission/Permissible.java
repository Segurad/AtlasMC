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
	default boolean hasPermission(String permission) {
		Permission perm = getPermission(permission);
		return perm != null ? perm.value() != 0 : false;
	}
	
	/**
	 * Returns the permission of this given string.
	 * If allow wild cards is true wild card permission checks will be performed
	 * @param permission
	 * @param allowWildcards
	 * @return permission or null
	 */
	Permission getPermission(String permission, boolean allowWildcards);
	
	/**
	 * Returns the permission of the given string with wild card checks
	 * @param permission
	 * @return permission or null
	 */
	default Permission getPermission(String permission) {
		return getPermission(permission, true);
	}
	
	/**
	 * Returns the value of the given permission or the given value if not present
	 * @param permission
	 * @param absent
	 * @return value or absent value
	 */
	default int getPermissionValue(String permission, int absent) {
		Permission perm = getPermission(permission);
		return perm != null ? perm.value() : absent;
	}

}
