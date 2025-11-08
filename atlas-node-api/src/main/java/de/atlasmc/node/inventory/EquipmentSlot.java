package de.atlasmc.node.inventory;

import de.atlasmc.IDHolder;
import de.atlasmc.util.enums.EnumName;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

public enum EquipmentSlot implements IDHolder, EnumName {
	
	/**
	 * Slot that defines any {@link EquipmentSlot}
	 */
	ANY("any", -1),
	MAIN_HAND("mainhand", 0),
	OFF_HAND("offhand", 5),
	/**
	 * Slot that defines a general hand slot {@link MainHand} or {@link #OFF_HAND}
	 */
	HAND("hand", -1),
	FEET("feet", 1),
	LEGS("legs", 2),
	CHEST("chest", 3),
	HEAD("head", 4),
	ARMOR("armor", 1),
	/**
	 * Slot that defines a general body slot {@link #HEAD}, {@link #CHEST}, {@link #LEGS} or {@link #FEET}
	 */
	BODY("body", 6);
	
	private static final Int2ObjectMap<EquipmentSlot> ID_TO_SLOT;
	
	static {
		Int2ObjectMap<EquipmentSlot> idToSlot = new Int2ObjectOpenHashMap<>();
		idToSlot.put(EquipmentSlot.MAIN_HAND.getEquippableID(), EquipmentSlot.MAIN_HAND);
		idToSlot.put(EquipmentSlot.FEET.getEquippableID(), EquipmentSlot.FEET);
		idToSlot.put(EquipmentSlot.LEGS.getEquippableID(), EquipmentSlot.LEGS);
		idToSlot.put(EquipmentSlot.CHEST.getEquippableID(), EquipmentSlot.CHEST);
		idToSlot.put(EquipmentSlot.HEAD.getEquippableID(), EquipmentSlot.HEAD);
		idToSlot.put(EquipmentSlot.OFF_HAND.getEquippableID(), EquipmentSlot.OFF_HAND);
		idToSlot.put(EquipmentSlot.BODY.getEquippableID(), EquipmentSlot.BODY);
		ID_TO_SLOT = Int2ObjectMaps.unmodifiable(idToSlot);
	}
	
	private final String name;
	private final int equippableID;
	
	private EquipmentSlot(String name, int equippableID) {
		this.name = name;
		this.equippableID = equippableID;
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public int getID() {
		return ordinal();
	}
	
	public int getEquippableID() {
		return equippableID;
	}
	
	public static EquipmentSlot getByEquippableID(int id) {
		return ID_TO_SLOT.get(id);
	}
	
	/**
	 * Whether or not the given slot is valid for this slot
	 * @param slot to check
	 * @return true if valid
	 */
	public boolean isValid(EquipmentSlot slot) {
		if (slot == null)
			return false;
		if (this == slot || this == ANY)
			return true;
		if (equippableID != -1) {
			return this == slot;
		} else if (this == HAND) {
			return slot == MAIN_HAND || slot == OFF_HAND;
		} else if (this == BODY) {
			switch (slot) {
			case HEAD:
			case BODY:
			case LEGS:
			case FEET:
				return true;
			default:
				return false;
			}
		}
		return false;
	}
	
}
