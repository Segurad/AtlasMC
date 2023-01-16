package de.atlasmc.permission;

import java.util.Collection;

/**
 * Stores all available {@link PermissionContext}s
 */
public interface PermissionContextHolder {
	
	public Collection<PermissionContext> getContexts();
	
	public Collection<PermissionContext> getContexts(String key);
	
	public PermissionContext getContext(String key, String context);
	
	public void addPermissionContext(PermissionContext context);
	
	public void removePermissionContext(PermissionContext context);
	
	public void removePermissionContext(String key, String context);

}
