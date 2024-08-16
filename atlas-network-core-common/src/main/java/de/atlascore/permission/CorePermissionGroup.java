package de.atlascore.permission;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import de.atlasmc.Color;
import de.atlasmc.chat.Chat;
import de.atlasmc.chat.ChatColor;
import de.atlasmc.permission.Permission;
import de.atlasmc.permission.PermissionContextProvider;
import de.atlasmc.permission.PermissionGroup;
import de.atlasmc.permission.PermissionGroupHolder;
import de.atlasmc.permission.PermissionHolder;
import de.atlasmc.util.annotation.InternalAPI;

public class CorePermissionGroup extends CorePermissionContextHolder implements PermissionGroup {

	private Chat prefix;
	private Chat suffix;
	private ChatColor chatColor;
	private Color chatColorRaw;
	private ChatColor nameColor;
	private boolean isDefault;
	private int power = 0;
	private int weight;
	private final PermissionHolder permissions;
	private final PermissionGroupHolder parents;
	private final String name;
	private final Map<String, String> contextProvider;
	
	public CorePermissionGroup(String name) {
		if (name == null)
			throw new IllegalArgumentException("Name can not be null!");
		this.name = name;
		this.permissions = new CorePermissionHolder();
		this.parents = new CorePermissionGroupHolder();
		this.contextProvider = new HashMap<>();
	}

	@Override
	public Collection<Permission> getPermissions() {
		return permissions.getPermissions();
	}

	@Override
	public void setPermission(String permission, int value) {
		permissions.setPermission(permission, value);
	}

	@Override
	public void setPermission(Permission permission) {
		permissions.setPermission(permission);
	}

	@Override
	public void removePermission(String permission) {
		permissions.removePermission(permission);		
	}

	@Override
	public void removePermission(Permission permission) {
		permissions.removePermission(permission);
	}

	@Override
	public int getPower() {
		return power;
	}

	@Override
	public Collection<PermissionGroup> getParents() {
		return parents.getGroups();
	}

	@Override
	public void addParent(PermissionGroup group) {
		parents.addPermissionGroup(group);
	}

	@Override
	public void removeParent(PermissionGroup group) {
		parents.removePermissionGroup(group);
	}

	@Override
	public Permission getPermission(String permission, PermissionContextProvider provider, boolean allowWildcards, boolean deepInheritance) {
		return internalGetPermission(permission, provider, allowWildcards, true, deepInheritance);
	}

	@Override
	public Permission getPermissionNoInheritance(String permission, PermissionContextProvider provider, boolean allowWildcards) {
		return internalGetPermission(permission, provider, allowWildcards, false, false);
	}
	
	private Permission internalGetPermission(String permission, PermissionContextProvider provider, boolean wildcards, boolean inheritance, boolean deep) {
		Permission perm = permissions.getPermission(permission, wildcards);
		if (perm != null)
			return perm;
		perm = super.getPermission(permission, provider, wildcards);
		if (perm != null)
			return perm;
		if (!inheritance)
			return null;
		Collection<PermissionGroup> parents = this.parents.getGroups();
		for (PermissionGroup parent : parents) {
			if (deep)
				perm = parent.getPermission(permission, provider, wildcards, true);
			else
				perm = parent.getPermissionNoInheritance(permission, provider, wildcards);
			if (perm != null)
				return perm;
		}
		return null;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public int getSortWeight() {
		return weight;
	}

	@InternalAPI
	public void setSortWeight(int weight) {
		this.weight = weight;
	}

	@Override
	public Chat getPrefix() {
		return prefix;
	}
	
	@InternalAPI
	public void setPrefix(Chat prefix) {
		this.prefix = prefix;
	}
	
	@Override
	public Chat getSuffix() {
		return suffix;
	}

	@InternalAPI
	public void setSuffix(Chat suffix) {
		this.suffix = suffix;
	}

	@Override
	public ChatColor getChatColor() {
		return chatColor == null ? null : chatColor;
	}
	
	@Override
	public Color getChatColorRaw() {
		return chatColor == null ? chatColorRaw : chatColor.getColor();
	}
	
	@InternalAPI
	public void setChatColor(ChatColor chatColor) {
		this.chatColorRaw = null;
		this.chatColor = chatColor;
	}
	
	public void setChatColor(Color color) {
		chatColor = null;
		this.chatColorRaw = color;
	}

	@Override
	public ChatColor getNameColor() {
		return nameColor;
	}
	
	@InternalAPI
	public void setNameColor(ChatColor nameColor) {
		this.nameColor = nameColor;
	}

	@InternalAPI
	public void setPower(int power) {
		this.power = power;
	}

	@Override
	public boolean isDefault() {
		return isDefault;
	}
	
	@InternalAPI
	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}

	@Override
	public Map<String, String> getContext() {
		return contextProvider;
	}

	@Override
	public void setContext(String key, String context) {
		contextProvider.put(key, context);
	}

	@Override
	public void removeContext(String key, String context) {
		contextProvider.remove(key, context);
	}

	@Override
	public String getContext(String key) {
		return contextProvider.get(key);
	}

}
