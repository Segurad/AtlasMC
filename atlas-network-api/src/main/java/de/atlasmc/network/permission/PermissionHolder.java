package de.atlasmc.network.permission;

import java.util.Collection;

import de.atlasmc.permission.Permissible;
import de.atlasmc.permission.Permission;
import de.atlasmc.util.annotation.InternalAPI;
import de.atlasmc.util.annotation.NotNull;

/**
 * Stores {@link Permission}s
 */
public interface PermissionHolder extends Permissible {
	
	@NotNull
	Collection<Permission> getPermissions();
	
	boolean setPermission(@NotNull CharSequence permission, int value);
	
	default boolean setPermission(@NotNull CharSequence permission) {
		return setPermission(permission, 1);
	}
	
	boolean setPermission(@NotNull Permission permission);
	
	boolean removePermission(@NotNull CharSequence permission);
	
	default boolean removePermission(@NotNull Permission permission) {
		return removePermission(permission.permission());
	}
	
	
	/**
	 * Whether or not the permissions have changed
	 * @return
	 */
	boolean hasChangedPermissions();
	
	/**
	 * Used to indicate that all changes have been persisted
	 */
	@InternalAPI
	void changedPermissions();
 
}
