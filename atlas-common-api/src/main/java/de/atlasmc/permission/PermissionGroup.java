package de.atlasmc.permission;

import java.util.Collection;

import de.atlasmc.Color;
import de.atlasmc.chat.Chat;
import de.atlasmc.chat.ChatColor;

public interface PermissionGroup extends PermissionHolder, PermissionContextHolder, PermissionContextProvider {
	
	int getPower();
	
	@Override
	default Permission getPermission(String permission, boolean allowWildcards) {
		return getPermission(permission, allowWildcards, false);
	}
	
	default Permission getPermission(String permission, boolean allowWildcards, boolean deepInheritance) {
		return getPermission(permission, null, allowWildcards, deepInheritance);
	}
	
	@Override
	default Permission getPermission(String permission, PermissionContextProvider context, boolean allowWildcards) {
		return getPermission(permission, context, allowWildcards, false);
	}
	
	/**
	 * Returns the permission of this given string. If allow wild cards is true wild card permission checks will be performed.
	 * @param permission the permission to check
	 * @param context the used context
	 * @param allowWildcards
	 * @param deepInheritance true if parents of parents should be checked
	 * @return permission or null
	 */
	Permission getPermission(String permission, PermissionContextProvider context, boolean allowWildcards, boolean deepInheritance);
	
	Permission getPermissionNoInheritance(String permission, PermissionContextProvider context, boolean allowWildcards);
	
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

}
