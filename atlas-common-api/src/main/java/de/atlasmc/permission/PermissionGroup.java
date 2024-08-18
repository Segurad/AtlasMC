package de.atlasmc.permission;

import java.util.Collection;

import de.atlasmc.Color;
import de.atlasmc.chat.Chat;
import de.atlasmc.chat.ChatColor;

public interface PermissionGroup extends PermissionHolder, PermissionContextHolder, ContextProvider {
	
	int getID();
	
	int getPower();
	
	default Permission getPermission(String permission) {
		return getPermission(permission, null, false);
	}
	
	default Permission getPermission(String permission, boolean shallow) {
		return getPermission(permission, null, shallow);
	}
	
	@Override
	default Permission getPermission(String permission, ContextProvider context) {
		return getPermission(permission, context, false);
	}
	
	/**
	 * Returns the permission of this given string. If allow wild cards is true wild card permission checks will be performed.
	 * @param permission the permission to check
	 * @param context the used context
	 * @param shallow true if parents and parents of parents should be checked
	 * @return permission or null
	 */
	Permission getPermission(String permission, ContextProvider context, boolean shallow);
	
	Collection<PermissionGroup> getParents();
	
	void addParent(PermissionGroup group);
	
	void removeParent(PermissionGroup group);

	String getName();

	int getSortWeight();

	Chat getPrefix();

	Chat getSuffix();

	ChatColor getChatColor();
	
	Color getChatColorRaw();

	ChatColor getNameColor();

	boolean isDefault();
	
	boolean hasGroupChanged();
	
	void changedGroup();
	
	boolean hasParentsChanged();
	
	void changedParents();

}
