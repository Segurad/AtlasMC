package de.atlasmc.core.network.permission;

import java.util.Collection;
import java.util.Objects;
import java.util.UUID;

import de.atlasmc.Color;
import de.atlasmc.chat.Chat;
import de.atlasmc.chat.ChatColor;
import de.atlasmc.network.permission.ContextProvider;
import de.atlasmc.network.permission.PermissionGroup;
import de.atlasmc.permission.Permission;
import de.atlasmc.util.annotation.InternalAPI;

public class CorePermissionGroup extends CorePermissionContextHolder implements PermissionGroup {

	private UUID uuid;
	private Chat prefix;
	private Chat suffix;
	private Color chatColor;
	private Color nameColor;
	private boolean isDefault;
	private int power;
	private int weight;
	private final CorePermissionHolder permissions;
	private final String name;
	private final ContextProvider contextProvider;
	
	private boolean groupChanged;
	
	public CorePermissionGroup(UUID uuid, String name) {
		this.uuid = Objects.requireNonNull(uuid);
		this.name = Objects.requireNonNull(name);
		this.permissions = new CorePermissionHolder();
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
			this.chatColor = null;
			return;
		}
		setChatColor(chatColor.asColor());
	}
	
	@Override
	public void setChatColor(Color color) {
		if (this.chatColor == color)
			return;
		if (color != null && color.equals(this.chatColor))
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
			this.nameColor = null;
			return;
		}
		setNameColor(color.asColor());
	}
	
	@Override
	public void setNameColor(Color color) {
		if (this.nameColor == color)
			return;
		if (color != null && color.equals(this.nameColor))
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
	
	@Override
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

	@Override
	public UUID getUUID() {
		return uuid;
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
	public Permission getPermission(CharSequence permission, ContextProvider context) {
		Permission perm = permissions.getPermission(permission);
		if (perm != null)
			return perm;
		if (context == null)
			return null;
		perm = super.getPermission(permission, context);
		if (perm != null)
			return perm;
		return null;
	}

	@Override
	public ContextProvider getContext() {
		return contextProvider;
	}

}
