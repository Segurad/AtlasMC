package de.atlasmc.inventory;

import java.util.List;

public enum EquipmentSlot {

	HAND,
	OFF_HAND,
	FEET,
	LEGS,
	CHEST,
	HEAD;
	
	private static List<EquipmentSlot> VALUES;

	public int getID() {
		return ordinal();
	}
	
	public static EquipmentSlot getByID(int id) {
		return getValues().get(id);
	}

	/**
	 * Returns a immutable List of all Types.<br>
	 * This method avoid allocation of a new array not like {@link #values()}.
	 * @return list
	 */
	public static List<EquipmentSlot> getValues() {
		if (VALUES == null)
			VALUES = List.of(values());
		return VALUES;
	}
	
}
