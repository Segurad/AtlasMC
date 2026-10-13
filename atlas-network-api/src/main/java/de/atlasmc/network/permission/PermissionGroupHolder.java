package de.atlasmc.network.permission;

import java.util.Collection;

import de.atlasmc.util.annotation.InternalAPI;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.annotation.Nullable;

public interface PermissionGroupHolder {
	
	/**
	 * Returns all {@link PermissionGroup}s of this holder
	 * @return groups
	 */
	@NotNull
	Collection<PermissionGroup> getGroups();
	
	/**
	 * Returns the PermissionGroup of this holder with the highest {@link PermissionGroup#getPower()}
	 * @return group
	 */
	@Nullable
	PermissionGroup getHighestGroup();
	
	/**
	 * Adds a {@link PermissionGroup} to this holder
	 * @param group to add
	 */
	void addPermissionGroup(@NotNull PermissionGroup group);
	
	/**
	 * Removes a {@link PermissionGroup} from this holder
	 * @param group
	 */
	void removePermissionGroup(@NotNull PermissionGroup group);
	
	/**
	 * Whether or not groups have changed.
	 * This does not indicate changes made to a group
	 * @return true if changed
	 */
	boolean hasGroupsChanged();
	
	/**
	 * Used to indicate that all changes have been persisted
	 */
	@InternalAPI
	void groupsChanged();

}
