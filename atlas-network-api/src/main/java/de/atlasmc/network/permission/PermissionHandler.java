package de.atlasmc.network.permission;

import java.util.UUID;

import de.atlasmc.permission.Permissible;
import de.atlasmc.util.annotation.NotNull;

/**
 * Handles all stuff regarding permissions
 */
public interface PermissionHandler extends PermissionGroupHolder, Permissible {
	
	@NotNull
	UUID getUUID();
	
	ContextProvider getContext();
	
	ContextProvider getTempContext();
	
	String getContext(String key);

}
