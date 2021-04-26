package de.atlasmc.inventory;

public enum EquipmentSlot {

	HAND,
	OFF_HAND,
	FEET,
	LEGS,
	CHEST,
	HEAD;

	public int getID() {
		return ordinal();
	}
	
	public static EquipmentSlot getByID(int id) {
		return values()[id];
	}
	
}
