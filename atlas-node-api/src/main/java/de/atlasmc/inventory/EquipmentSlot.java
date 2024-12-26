package de.atlasmc.inventory;

import java.util.List;

import de.atlasmc.util.EnumID;
import de.atlasmc.util.EnumName;
import de.atlasmc.util.EnumValueCache;

public enum EquipmentSlot implements EnumID, EnumName, EnumValueCache {

	/**
	 * Slot that defines any {@link EquipmentSlot}
	 */
	ANY("any"),
	MAIN_HAND("mainhand"),
	OFF_HAND("offhand"),
	/**
	 * Slot that defines a general hand slot {@link MainHand} or {@link #OFF_HAND}
	 */
	HAND("hand"),
	FEET("feet"),
	LEGS("legs"),
	CHEST("chest"),
	HEAD("head"),
	ARMOR("armor"),
	/**
	 * Slot that defines a general body slot {@link #HEAD}, {@link #CHEST}, {@link #LEGS} or {@link #FEET}
	 */
	BODY("body");
	
	private static List<EquipmentSlot> VALUES;
	
	private String name;
	
	private EquipmentSlot(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public static EquipmentSlot getByName(String name) {
		List<EquipmentSlot> values = getValues();
		final int size = values.size();
		for (int i = 0; i < size; i++) {
			EquipmentSlot slot = values.get(i);
			if (slot.name.equals(name))
				return slot;
		}
		return null;
	}

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
