package de.atlasmc.inventory;

import java.util.List;

public enum ItemFlag {
	
	HIDE_ENCHANTS,
	HIDE_ATTRIBUTES,
	HIDE_DESTROYS,
	HIDE_DYE,
	HIDE_UNBREAKABLE,
	HIDE_CANDESTROY,
	HIDE_PLACED_ON,
	HIDE_POTION_EFFECTS;
	
	private static List<ItemFlag> VALUES;
	
	/**
	 * Returns a immutable List of all Types.<br>
	 * This method avoid allocation of a new array not like {@link #values()}.
	 * @return list
	 */
	public static List<ItemFlag> getValues() {
		if (VALUES == null)
			VALUES = List.of(values());
		return VALUES;
	}

	/**
	 * Releases the system resources used from the values cache
	 */
	public static void freeValues() {
		VALUES = null;
	}

}
