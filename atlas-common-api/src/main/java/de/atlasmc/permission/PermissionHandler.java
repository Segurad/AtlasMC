package de.atlasmc.permission;

/**
 * Handles all stuff regarding permissions
 */
public interface PermissionHandler extends PermissionGroupHolder, PermissionHolder, PermissionContextHolder {
	
	ContextProvider getContext();
	
	ContextProvider getTempContext();
	
	String getContext(String key);
	
	/**
	 * Returns the permission of set this handler.
	 * @param permission
	 * @return permission
	 */
	Permission getHandlerPermission(CharSequence permission);

}
