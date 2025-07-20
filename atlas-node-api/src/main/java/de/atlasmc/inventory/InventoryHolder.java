package de.atlasmc.inventory;

import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface InventoryHolder {
	
	public static final NBTSerializationHandler<InventoryHolder>
	NBT_HANDLER = NBTSerializationHandler
					.builder(InventoryHolder.class)
					.typeArraySearchByteIndexField("Items", "Slot", InventoryHolder::hasInventory, (value) -> { return value.getInventory().getContentsUnsafe(); }, ItemStack.NBT_HANDLER)
					.build();
	
	/**
	 * Returns the Inventory of this holder
	 * @return inventory
	 */
	Inventory getInventory();
	
	/**
	 * Returns whether or not a inventory has been set or initialized
	 * @return true if a inventory has been initialized
	 */
	boolean hasInventory();

}
