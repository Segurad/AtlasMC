package de.atlasmc.permission;

import java.util.UUID;

/**
 * Handles all stuff regarding permissions
 */
public interface PermissionHandler extends PermissionGroupHolder, PermissionHolder, PermissionContextHolder, ContextProvider {
	
	UUID getUUID();

}
