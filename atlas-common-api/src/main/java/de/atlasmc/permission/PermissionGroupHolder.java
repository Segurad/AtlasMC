package de.atlasmc.permission;

import java.util.Collection;

public interface PermissionGroupHolder {
	
	/**
	 * Returns all {@link PermissionGroup}s of this holder
	 * @return groups
	 */
	Collection<PermissionGroup> getGroups();
	
	/**
	 * Returns the PermissionGroup of this holder with the highest {@link PermissionGroup#getPower()}
	 * @return group
	 */
	PermissionGroup getHighestGroup();
	
	/**
	 * Adds a {@link PermissionGroup} to this holder
	 * @param group to add
	 */
	void addPermissionGroup(PermissionGroup group);
	
	/**
	 * Removes a {@link PermissionGroup} from this holder
	 * @param group
	 */
	void removePermissionGroup(PermissionGroup group);

}
