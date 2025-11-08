package de.atlasmc.node.inventory;

import de.atlasmc.nbt.codec.NBTCodec;

public interface InventoryHolder {
	
	public static final NBTCodec<InventoryHolder>
	NBT_HANDLER = NBTCodec
					.builder(InventoryHolder.class)
					.codecArraySearchByteIndexField("Items", "Slot", InventoryHolder::hasInventory, (value) -> { return value.getInventory().getContentsUnsafe(); }, ItemStack.NBT_HANDLER)
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
