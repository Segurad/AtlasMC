package de.atlasmc.network.permission;

import java.util.Collection;

import de.atlasmc.permission.Permission;
import de.atlasmc.util.annotation.InternalAPI;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.annotation.Nullable;

/**
 * Stores all available {@link PermissionContext}s
 */
public interface PermissionContextHolder {
	
	@NotNull
	Collection<PermissionContext> getPermissionContexts();
	
	@NotNull
	Collection<PermissionContext> getPermissionContexts(String key);
	
	@Nullable
	PermissionContext getPermissionContext(@NotNull String key, @NotNull String context);
	
	/**
	 * Returns the permission of this given string.
	 * @param permission the permission to check
	 * @param context the used context
	 * @return permission or null
	 */
	@Nullable
	Permission getPermission(@NotNull CharSequence permission, @NotNull ContextProvider provider);
	
	boolean addPermissionContext(@NotNull PermissionContext context);
	
	boolean removePermissionContext(@NotNull PermissionContext context);
	
	boolean removePermissionContext(@NotNull String key, @NotNull String context);
	
	/**
	 * Whether or not the the holder has changed.
	 * Does not indicate that a context has changed.
	 * @return true if changed
	 */
	boolean hasChangedPermissionContext();
	
	/**
	 * Used to indicate that all changes have been persisted
	 */
	@InternalAPI
	void changedPermissionContexts();

}
