package de.atlasmc.event.inventory;

import de.atlasmc.chat.component.ChatComponent;
import de.atlasmc.factory.ContainerFactory;
import de.atlasmc.inventory.Inventory;
import de.atlasmc.inventory.InventoryHolder;

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
	WORKBENCH(11),
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
	HORSE(-1), // Opened via PacketOutOpenHorseWindow
	LLAMA(-1),
	PLAYER(3),
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
	/**
	 * The crafting section of the player's inventory<br>
	 * Containing 4 {@link SlotType#CRAFTING} and 1 {@link SlotType#RESULT}
	 */
	CRAFTING(-1)
	;
	
	private final int id;

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
	
	public Inventory create(ChatComponent title, InventoryHolder holder) {
		switch(this) {
		case CHEST:
		case BARREL:
		case DOUBLE_CHEST:
		case DISPENSER:
		case DROPPER:
		case ENDER_CHEST:
		case GENERIC_3X3:
		case GENERIC_9X1:
		case GENERIC_9X2:
		case GENERIC_9X3:
		case GENERIC_9X4:
		case GENERIC_9X5:
		case GENERIC_9X6:
		case SHULKER_BOX:
		case HOPPER:
			return ContainerFactory.GENERIC_INV_FACTORY.create(this, title, holder);
		case ANVIL:
			return ContainerFactory.ANVIL_INV_FACTORY.create(this, title, holder);
		case BEACON:
			return ContainerFactory.BEACON_INV_FACTORY.create(this, title, holder);
		case BLAST_FURNACE:
			return ContainerFactory.BLAST_FURNACE_INV_FACTORY.create(this, title, holder);
		case BREWING:
			return ContainerFactory.BREWING_INV_FACTORY.create(this, title, holder);
		case CARTOGRAPHY:
			return ContainerFactory.CARTOGRAPHY_INV_FACTORY.create(this, title, holder);
		case CRAFTING:
			return ContainerFactory.CRAFTING_INV_FACTORY.create(this, title, holder);
		case ENCHANTING:
			return ContainerFactory.ENCHANTING_INV_FACTORY.create(this, title, holder);
		case FURNACE:
			return ContainerFactory.FURNACE_INV_FACTPRY.create(this, title, holder);
		case GRINDSTONE:
			return ContainerFactory.GRINDSTONE_INV_FACTORY.create(this, title, holder);
		case HORSE:
			return ContainerFactory.HORSE_INV_FACTORY.create(this, title, holder);
		case LECTERN:
			return ContainerFactory.LECTERN_INV_FACTORY.create(this, title, holder);
		case LLAMA:
			return ContainerFactory.LLAMA_INV_FACTORY.create(this, title, holder);
		case LOOM:
			return ContainerFactory.LOOM_INV_FACTORY.create(this, title, holder);
		case MERCHANT:
			return ContainerFactory.MERCHANT_INV_FACTORY.create(this, title, holder);
		case PLAYER:
			return ContainerFactory.PLAYER_INV_FACTORY.create(this, title, holder);
		case SMITHING:
			return ContainerFactory.SMITHING_INV_FACTORY.create(this, title, holder);
		case SMOKER:
			return ContainerFactory.SMOKER_INV_FACTORY.create(this, title, holder);
		case STONECUTTER:
			return ContainerFactory.STONECUTTER_INV_FACTORY.create(this, title, holder);
		case WORKBENCH:
			return ContainerFactory.WORKBENCH_INV_FACTORY.create(this, title, holder);
		default:
			return null;
		}
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
