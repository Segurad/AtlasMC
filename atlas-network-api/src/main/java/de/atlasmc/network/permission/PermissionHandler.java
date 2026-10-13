package de.atlasmc.network.permission;

import java.util.UUID;

import de.atlasmc.IDHolder;
import de.atlasmc.permission.Permissible;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.annotation.Nullable;

/**
 * Handles all stuff regarding permissions
 */
public interface PermissionHandler extends PermissionGroupHolder, Permissible, IDHolder {
	
	@NotNull
	UUID getUUID();
	
	/**
	 * Returns the persistent context
	 * @return context
	 */
	@NotNull
	ContextProvider getContext();
	
	/**
	 * Returns the temporary non persistent context
	 * @return context
	 */
	@NotNull
	ContextProvider getTempContext();
	
	/**
	 * Returns the value assigned to the key of the context.
	 * The temporary context is checked first.
	 * @param key
	 * @return value or null
	 */
	@Nullable
	String getContext(@NotNull String key);

}
