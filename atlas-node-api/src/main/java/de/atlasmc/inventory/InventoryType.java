package de.atlasmc.inventory;

import java.util.List;

import de.atlasmc.chat.Chat;
import de.atlasmc.io.protocol.play.PacketOutOpenScreen;
import de.atlasmc.util.EnumID;
import de.atlasmc.util.EnumValueCache;

public enum InventoryType implements EnumID, EnumValueCache {

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
	LLAMA(-2),
	// --- non standard (types that are opened differently or only internal)
	/**
	 * A {@link PlayerInventory} only used internal.
	 * Will open as {@link InventoryType#GENERIC_9X3} on client.
	 */
	PLAYER(3),
	/**
	 * A dispenser inventory, with 9 slots of {@link SlotType#CONTAINER}.<br>
	 * Alternative to {@link InventoryType#GENERIC_3X3} for differentiation.
	 * <br>
	 * If you would like to create a custom inventory do not use this.
	 */
	DISPENSER(6),
	/**
	 * A dropper inventory, with 9 slots of {@link SlotType#CONTAINER}.<br>
	 * Alternative to {@link InventoryType#GENERIC_3X3} for differentiation.
	 * <br>
	 * If you would like to create a custom inventory do not use this.
	 */
	DROPPER(6),
	/**
	 * A barrel inventory, with 27 slots of {@link SlotType#CONTAINER}.<br>
	 * Alternative to {@link InventoryType#GENERIC_9X3} for differentiation.
	 * <br>
	 * If you would like to create a custom inventory do not use this.
	 */
	BARREL(2),
	/**
	 * A ender chest inventory, with 27 slots of {@link SlotType#CONTAINER}.<br>
	 * Alternative to {@link InventoryType#GENERIC_9X3} for differentiation.<br>
	 * <br>
	 * If you would like to create a custom inventory or ender chest do not use this.
	 */
	ENDER_CHEST(2),
	/**
	 * A chest inventory, with 27 slots of {@link SlotType#CONTAINER}.<br>
	 * Alternative to {@link InventoryType#GENERIC_9X3} for differentiation.<br>
	 * <br>
	 * If you would like to create a custom inventory do not use this.
	 */
	CHEST(2),
	/**
	 * A double chest inventory, with 54 slots of {@link SlotType#CONTAINER}.<br>
	 * Alternative to {@link InventoryType#GENERIC_9X6} for differentiation.<br>
	 * <br>
	 * If you would like to create a custom inventory do not use this.
	 */
	DOUBLE_CHEST(5),
	/**
	 * The crafting section of the player's inventory<br>
	 * Containing 4 {@link SlotType#CRAFTING} and 1 {@link SlotType#RESULT}
	 */
	CRAFTING(-1)
	;
	
	private static List<InventoryType> VALUES;
	
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
	 * Returns the protocol id.
	 * Id's with negative values have dedicated packets an may not be opened with {@link PacketOutOpenScreen}
	 * @return id for protocol
	 */
	public int getID() {
		return id;
	}
	
	/**
	 * Returns a immutable List of all Types.<br>
	 * This method avoid allocation of a new array not like {@link #values()}.
	 * @return list
	 */
	public static List<InventoryType> getValues() {
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
	
	public Inventory create(Chat title) {
		return create(title, null);
	}
	
	public Inventory create(InventoryHolder holder) {
		return create(null, holder);
	}
	
	public Inventory create(Chat title, InventoryHolder holder) {
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
			throw new IllegalStateException("Unimplemented create for type: " + this.name());
		}
	}
	
	public static InventoryType getByID(int id) {
		for (InventoryType t : getValues()) {
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
