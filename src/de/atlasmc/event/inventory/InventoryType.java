package de.atlasmc.event.inventory;

public enum InventoryType {

	GENERIC_9X1(0),
	GENERIC_9X2(1),
	GENERIC_9X3(2),
	GENERIC_9X4(3),
	GENERIC_9X5(4),
	GENERIC_9X6(5),
	GENERIC_3X3(6),
	ANVIL(7),
	BEACON(8),
	BLAST_FURNACE(9),
	BREWING(10),
	/**
	 * A crafting table inventory, with 9 {@link SlotType#CRAFTING} slots and a {@link SlotType#RESULT} slot.
	 */
	CRAFTING(11),
	ENCHANTING(12),
	FURNACE(13),
	GRINDSTONE(14),
	HOPPER(15),
	LECTERN(16),
	LOOM(17),
	MERCHANT(18),
	SHULKER_BOX(19),
	SMITHING(20),
	SMOKER(21),
	CARTOGRAPHY(22),
	STONECUTTER(23),
	// --- non standard (types that are opened differently or only internal)
	/**
	 * A dispenser inventory, with 9 slots of {@link SlotType#CONTAINER}.<br>
	 * Alternative to {@link InventoryType#GENERIC_3X3} for differentiation.
	 */
	DISPENSER(6),
	/**
	 * A dropper inventory, with 9 slots of {@link SlotType#CONTAINER}.<br>
	 * Alternative to {@link InventoryType#GENERIC_3X3} for differentiation.
	 */
	DROPPER(6),
	/**
	 * A barrel inventory, with 27 slots of {@link SlotType#CONTAINER}.<br>
	 * Alternative to {@link InventoryType#GENERIC_9X3} for differentiation.
	 */
	BARREL(2),
	/**
	 * A ender chest inventory, with 27 slots of {@link SlotType#CONTAINER}.<br>
	 * Alternative to {@link InventoryType#GENERIC_9X3} for differentiation.<br>
	 * <br>
	 * If you would like to create a custom ender chest with different size do not use this.
	 */
	ENDER_CHEST(2),
	/**
	 * A chest inventory, with 27 slots of {@link SlotType#CONTAINER}.<br>
	 * Alternative to {@link InventoryType#GENERIC_9X3} for differentiation.<br>
	 * <br>
	 * If you would like to create a custom inventory with different size do not use this.
	 */
	CHEST(2),
	/**
	 * A double chest inventory, with 54 slots of {@link SlotType#CONTAINER}.<br>
	 * Alternative to {@link InventoryType#GENERIC_9X6} for differentiation.<br>
	 * <br>
	 * If you would like to create a custom inventory with different size do not use this.
	 */
	DOUBLE_CHEST(5),
	CREATIVE(-1),
	HORSE(-1), // Opened via PacketOutOpenHorseWindow
	LLAMA(-1),
	PLAYER(3),
	;
	
	private int id;
	
	private InventoryType(int id) {
		this.id = id;
	}
	
	public boolean isChest() {
		switch (this) {
		case GENERIC_9X1:
		case GENERIC_9X2:
		case GENERIC_9X3:
		case GENERIC_9X4:
		case GENERIC_9X5:
		case GENERIC_9X6:
			return true;
		default: return false;
		}
	}
	
	/**
	 * 
	 * @return id for protocol
	 */
	public int getID() {
		return id;
	}
	
	public static InventoryType getByID(int id) {
		for (InventoryType t : values()) {
			if (t.getID() == id) return t;
		}
		return null;
	}
	
	public static enum SlotType {
		ARMOR,
		CONTAINER,
		CRAFTING,
		FUEL,
		OUTSIDE,
		QUICKBAR,
		RESULT;
	}

	public int getSize() {
		return 0;
	}
}
