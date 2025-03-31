package de.atlasmc.permission;

import java.util.UUID;

/**
 * Handles all stuff regarding permissions
 */
public interface PermissionHandler extends PermissionGroupHolder, Permissible {
	
	UUID getUUID();
	
	ContextProvider getContext();
	
	ContextProvider getTempContext();
	
	String getContext(String key);

}
