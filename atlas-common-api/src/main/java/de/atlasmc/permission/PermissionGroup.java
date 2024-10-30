package de.atlasmc.permission;

import java.util.Comparator;

import de.atlasmc.Color;
import de.atlasmc.chat.Chat;
import de.atlasmc.chat.ChatColor;

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
	
	default Permission getPermission(CharSequence permission) {
		return getPermission(permission, null);
	}
	
	/**
	 * Returns the permission of this given string.
	 * @param permission the permission to check
	 * @param context the used context
	 * @return permission or null
	 */
	Permission getPermission(CharSequence permission, ContextProvider context);

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

	int getID();

}
