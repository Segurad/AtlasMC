package de.atlascore.permission;

import java.util.Collection;

import de.atlasmc.Color;
import de.atlasmc.chat.Chat;
import de.atlasmc.chat.ChatColor;
import de.atlasmc.permission.ContextProvider;
import de.atlasmc.permission.Permission;
import de.atlasmc.permission.PermissionGroup;
import de.atlasmc.permission.PermissionGroupHolder;
import de.atlasmc.util.annotation.InternalAPI;

public class CorePermissionGroup extends CorePermissionContextHolder implements PermissionGroup {

	private int id;
	private Chat prefix;
	private Chat suffix;
	private Color chatColor;
	private Color nameColor;
	private boolean isDefault;
	private int power;
	private int weight;
	private final CorePermissionHolder permissions;
	private final PermissionGroupHolder parents;
	private final String name;
	private final ContextProvider contextProvider;
	
	private boolean groupChanged;
	
	public CorePermissionGroup(int id, String name) {
		if (name == null)
			throw new IllegalArgumentException("Name can not be null!");
		this.id = id;
		this.name = name;
		this.permissions = new CorePermissionHolder();
		this.parents = new CorePermissionGroupHolder();
		this.contextProvider = new CoreContextProvider();
	}
	
	CorePermissionHolder permissions() {
		return permissions;
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
	public String getName() {
		return name;
	}
	
	@Override
	public int getSortWeight() {
		return weight;
	}

	@Override
	public void setSortWeight(int weight) {
		if (this.weight == weight)
			return;
		this.weight = weight;
		groupChanged = true;
	}

	@Override
	public Chat getPrefix() {
		return prefix;
	}
	
	@Override
	public void setPrefix(Chat prefix) {
		if (this.prefix == prefix)
			return;
		if (prefix != null && prefix.equals(this.prefix))
			return;
		this.prefix = prefix;
		groupChanged = true;
	}
	
	@Override
	public Chat getSuffix() {
		return suffix;
	}

	@Override
	public void setSuffix(Chat suffix) {
		if (this.suffix == suffix)
			return;
		if (suffix != null && suffix.equals(this.suffix))
			return;
		this.suffix = suffix;
		groupChanged = true;
	}

	@Override
	public Color getChatColor() {
		return chatColor;
	}
	
	@Override
	public void setChatColor(ChatColor chatColor) {
		if (chatColor == null) {
			if (this.chatColor == null)
				return;
			groupChanged = true;
		}
		setChatColor(chatColor.getColor());
	}
	
	@Override
	public void setChatColor(Color color) {
		if (this.chatColor == color)
			return;
		if (color != null && this.chatColor.equals(color))
			return;
		this.chatColor = color;
		groupChanged = true;
	}

	@Override
	public Color getNameColor() {
		return nameColor;
	}
	
	@Override
	public void setNameColor(ChatColor color) {
		if (color == null) {
			if (this.nameColor == null)
				return;
			groupChanged = true;
		}
		setNameColor(color.getColor());
	}
	
	@Override
	public void setNameColor(Color color) {
		if (this.nameColor == color)
			return;
		if (color != null && this.nameColor.equals(color))
			return;
		this.nameColor = color;
		groupChanged = true;
	}

	@Override
	public void setPower(int power) {
		if (this.power == power)
			return;
		this.power = power;
		groupChanged = true;
	}

	@Override
	public boolean isDefault() {
		return isDefault;
	}
	
	@InternalAPI
	public void setDefault(boolean isDefault) {
		if (this.isDefault == isDefault)
			return;
		this.isDefault = isDefault;
		groupChanged = true;
	}

	@Override
	public boolean hasChangedPermissions() {
		return permissions.hasChangedPermissions();
	}

	@Override
	public void changedPermissions() {
		permissions.changedPermissions();
	}

	public int getID() {
		return id;
	}

	@Override
	public boolean hasGroupChanged() {
		return groupChanged;
	}

	@Override
	public void changedGroup() {
		groupChanged = false;
	}

	@Override
	public boolean hasParentsChanged() {
		return parents.hasGroupsChanged();
	}

	@Override
	public void changedParents() {
		parents.groupsChanged();
	}

	@Override
	public Permission getPermission(CharSequence permission, ContextProvider context, boolean shallow) {
		Permission perm = permissions.getPermission(permission);
		if (perm != null)
			return perm;
		if (context == null)
			return null;
		perm = super.getPermission(permission, context);
		if (perm != null)
			return perm;
		if (shallow)
			return null;
		Collection<PermissionGroup> parents = this.parents.getGroups();
		for (PermissionGroup parent : parents) {
			perm = parent.getPermission(permission, context, true);
			if (perm != null)
				return perm;
		}
		return null;
	}

	@Override
	public ContextProvider getContext() {
		return contextProvider;
	}

}
