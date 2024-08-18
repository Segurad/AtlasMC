package de.atlasmc.permission;

import java.util.Collection;

/**
 * Stores {@link Permission}s
 */
public interface PermissionHolder extends Permissible {
	
	Collection<Permission> getPermissions();
	
	void setPermission(String permission, int value);
	
	default void setPermission(String permission) {
		setPermission(permission, 1);
	}
	
	void setPermission(Permission permission);
	
	void removePermission(String permission);
	
	void removePermission(Permission permission);
	
	boolean hasChangedPermissions();
	
	/**
	 * Used to indicate that all changes have been persisted
	 */
	void changedPermissions();
 
}
