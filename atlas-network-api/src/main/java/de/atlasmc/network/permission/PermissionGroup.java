package de.atlasmc.network.permission;

import java.util.Comparator;

import de.atlasmc.Color;
import de.atlasmc.IDHolder;
import de.atlasmc.chat.Chat;
import de.atlasmc.permission.Permission;
import de.atlasmc.util.annotation.InternalAPI;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.annotation.Nullable;

public interface PermissionGroup extends PermissionHolder, PermissionContextHolder, IDHolder {
	
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
	
	@NotNull
	ContextProvider getContext();
	
	@Nullable
	@Override
	default Permission getPermission(@NotNull CharSequence permission) {
		return getPermission(permission, null);
	}

	@NotNull
	String getName();

	int getSortWeight();
	
	void setSortWeight(int weight);

	@Nullable
	Chat getPrefix();
	
	void setPrefix(@Nullable Chat chat);

	@Nullable
	Chat getSuffix();
	
	void setSuffix(@Nullable Chat chat);

	@Nullable
	Color getChatColor();
	
	void setChatColor(@Nullable Color color);

	@Nullable
	Color getNameColor();
	
	void setNameColor(@Nullable Color color);

	boolean isDefault();
	
	void setDefault(boolean isDefault);
	
	/**
	 * Whether or not the group has changed
	 * @return true if changed
	 */
	boolean hasGroupChanged();
	
	/**
	 * Used to indicate that all changes have been persisted
	 */
	@InternalAPI
	void changedGroup();

}
