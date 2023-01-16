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
	public boolean hasPermission(String permission);
	
	/**
	 * Returns the permission of the given string
	 * @param permission
	 * @return permission or null
	 */
	public Permission getPermission(String permission);
	
	/**
	 * Returns the value of the given permission or the given value if not present
	 * @param permission
	 * @param absent
	 * @return value or absent value
	 */
	public int getPermissionValue(String permission, int absent);

}
