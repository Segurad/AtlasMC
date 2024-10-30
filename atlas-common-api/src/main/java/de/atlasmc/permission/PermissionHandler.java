package de.atlasmc.permission;

/**
 * Handles all stuff regarding permissions
 */
public interface PermissionHandler extends PermissionGroupHolder, Permissible {
	
	int getID();
	
	ContextProvider getContext();
	
	ContextProvider getTempContext();
	
	String getContext(String key);

}
