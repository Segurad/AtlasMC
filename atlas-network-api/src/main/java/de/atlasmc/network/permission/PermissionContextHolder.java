package de.atlasmc.network.permission;

import java.util.Collection;

import de.atlasmc.permission.Permission;

/**
 * Stores all available {@link PermissionContext}s
 */
public interface PermissionContextHolder {
	
	Collection<PermissionContext> getPermissionContexts();
	
	Collection<PermissionContext> getPermissionContexts(String key);
	
	PermissionContext getPermissionContext(String key, String context);
	
	/**
	 * Returns the permission of this given string.
	 * @param permission the permission to check
	 * @param context the used context
	 * @return permission or null
	 */
	Permission getPermission(CharSequence permission, ContextProvider provider);
	
	boolean addPermissionContext(PermissionContext context);
	
	boolean removePermissionContext(PermissionContext context);
	
	boolean removePermissionContext(String key, String context);
	
	boolean hasChangedPermissionContext();
	
	void changedPermissionContexts();

}
