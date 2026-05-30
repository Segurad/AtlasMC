package de.atlasmc.node.inventory;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.annotation.Nullable;

public interface InventoryHolder {
	
	@NotNull
	public static final NBTCodec<InventoryHolder>
	NBT_CODEC = NBTCodec
					.builder(InventoryHolder.class)
					.codecArraySearchByteIndexField("Items", "Slot", InventoryHolder::hasInventory, (value) -> { return value.getInventory().getContentsUnsafe(); }, ItemStack.NBT_CODEC)
					.build();
	
	/**
	 * Returns the Inventory of this holder
	 * @return inventory
	 */
	@Nullable
	Inventory getInventory();
	
	/**
	 * Returns whether or not a inventory has been set or initialized
	 * @return true if a inventory has been initialized
	 */
	boolean hasInventory();

}
