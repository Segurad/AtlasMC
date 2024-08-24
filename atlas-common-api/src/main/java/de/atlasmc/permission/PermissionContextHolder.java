package de.atlasmc.permission;

import java.util.Collection;

/**
 * Stores all available {@link PermissionContext}s
 */
public interface PermissionContextHolder {
	
	Collection<PermissionContext> getPermissionContexts();
	
	Collection<PermissionContext> getPermissionContexts(String key);
	
	PermissionContext getPermissionContext(String key, String context);
	
	Permission getPermission(CharSequence permission, ContextProvider provider);
	
	boolean addPermissionContext(PermissionContext context);
	
	boolean removePermissionContext(PermissionContext context);
	
	boolean removePermissionContext(String key, String context);
	
	boolean hasChangedPermissionContext();
	
	void changedPermissionContexts();

}
