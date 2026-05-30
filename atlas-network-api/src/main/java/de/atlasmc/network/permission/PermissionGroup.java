package de.atlasmc.network.permission;

import java.util.Comparator;
import java.util.UUID;

import de.atlasmc.Color;
import de.atlasmc.chat.Chat;
import de.atlasmc.chat.ChatColor;
import de.atlasmc.permission.Permission;
import de.atlasmc.util.annotation.NotNull;

public interface PermissionGroup extends PermissionHolder, PermissionContextHolder {
	
	/**
	 * Sorts {@link PermissionGroup}s by {@link #getPower()} in descending order.
	 */
	public static final Comparator<PermissionGroup> SORT_BY_POWER = (a, b) -> {
		if (a == b)
			return 0;
		int powerA = a.getPower();
		int powerB = b.getPower();
		if (powerA == powerB)
			return 0;
		return powerA > powerB ? -1 : 1;
	};
	
	/**
	 * Sorts {@link PermissionGroup}s by {@link #getSortWeight()} in descending order.
	 * If sort weight is equal {@link #getName()} is used
	 */
	public static final Comparator<PermissionGroup> SORT_BY_WEIGHT = (a, b) -> {
		if (a == b)
			return 0;
		int weightA = a.getSortWeight();
		int weightB = b.getSortWeight();
		if (weightA == weightB)
			return a.getName().compareTo(b.getName());
		return weightA > weightB ? -1 : 1;
	};
	
	int getPower();
	
	void setPower(int power);
	
	ContextProvider getContext();
	
	@Override
	default Permission getPermission(CharSequence permission) {
		return getPermission(permission, null);
	}

	String getName();

	int getSortWeight();
	
	void setSortWeight(int weight);

	Chat getPrefix();
	
	void setPrefix(Chat chat);

	Chat getSuffix();
	
	void setSuffix(Chat chat);

	Color getChatColor();
	
	void setChatColor(ChatColor color);
	
	void setChatColor(Color color);

	Color getNameColor();
	
	void setNameColor(ChatColor color);
	
	void setNameColor(Color color);

	boolean isDefault();
	
	void setDefault(boolean isDefault);
	
	boolean hasGroupChanged();
	
	void changedGroup();

	@NotNull
	UUID getUUID();

}
