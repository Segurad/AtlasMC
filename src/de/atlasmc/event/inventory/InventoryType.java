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
	// non standard (types that are opened differently or only internal)
	DISPENSER(6),
	DROPPER(6),
	BARREL(2),
	ENDER_CHEST(2),
	CREATIVE(-1),
	WORKBENCH(-1),
	HORSE(-1),
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
}
