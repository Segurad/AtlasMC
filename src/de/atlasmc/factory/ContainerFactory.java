package de.atlasmc.factory;

import de.atlasmc.event.inventory.InventoryType;
import de.atlasmc.inventory.BeaconInventory;
import de.atlasmc.inventory.Inventory;
import de.atlasmc.inventory.InventoryHolder;
import de.atlasmc.inventory.PlayerInventory;

/**
 * Factory for creating Containers Like Inventories or GUIs
 */
public abstract class ContainerFactory<I extends Inventory> {
	
	/**
	 * Factory that creates all types of generic inventories<br>
	 * 
	 * List of Types:<br>
	 * {@link InventoryType#GENERIC_9X1},<br>
	 * {@link InventoryType#GENERIC_9X2},<br>
	 * {@link InventoryType#GENERIC_9X3},<br>
	 * {@link InventoryType#GENERIC_9X4},<br>
	 * {@link InventoryType#GENERIC_9X5},<br>
	 * {@link InventoryType#GENERIC_9X6},<br>
	 * {@link InventoryType#GENERIC_3X3},<br>
	 * {@link InventoryType#CHEST},<br>
	 * {@link InventoryType#DOUBLE_CHEST},<br>
	 * {@link InventoryType#ENDER_CHEST},<br>
	 * {@link InventoryType#DISPENSER},<br>
	 * {@link InventoryType#DROPPER},<br>
	 * {@link InventoryType#BARREL},<br>
	 * {@link InventoryType#SHULKER_BOX}
	 */
	public static ContainerFactory<Inventory> GENERIC_INV_FACTORY;
	
	/**
	 * Creates inventories of the type {@link InventoryType#BEACON}
	 */
	public static ContainerFactory<BeaconInventory> BEACON_INV_FACTORY;
	
	/**
	 * Creates inventories of the type {@link InventoryType#PLAYER}
	 */
	public static ContainerFactory<PlayerInventory> PLAYER_INV_FACTORY;
	
	public I createContainer(InventoryType type, InventoryHolder holder) {
		return createContainer(type, holder, null);
	}
	
	public abstract I createContainer(InventoryType type, InventoryHolder holder, String title);

}
