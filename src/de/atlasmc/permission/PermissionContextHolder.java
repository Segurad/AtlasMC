package de.atlasmc.permission;

import java.util.Collection;

/**
 * Stores all available {@link PermissionContext}s
 */
public interface PermissionContextHolder {
	
	Collection<PermissionContext> getContexts();
	
	Collection<PermissionContext> getContexts(String key);
	
	PermissionContext getContext(String key, String context);
	
	boolean addPermissionContext(PermissionContext context);
	
	boolean removePermissionContext(PermissionContext context);
	
	boolean removePermissionContext(String key, String context);

}
