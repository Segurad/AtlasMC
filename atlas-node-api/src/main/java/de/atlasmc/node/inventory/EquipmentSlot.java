package de.atlasmc.node.inventory;

import de.atlasmc.IDHolder;
import de.atlasmc.util.EnumName;

public enum EquipmentSlot implements IDHolder, EnumName {

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
	
	private String name;
	
	private EquipmentSlot(String name) {
		this.name = name;
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public int getID() {
		return ordinal();
	}
	
}
