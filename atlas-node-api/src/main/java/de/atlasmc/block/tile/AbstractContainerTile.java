package de.atlasmc.block.tile;

import de.atlasmc.Nameable;
import de.atlasmc.chat.Chat;
import de.atlasmc.inventory.Inventory;
import de.atlasmc.inventory.InventoryHolder;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface AbstractContainerTile<I extends Inventory> extends TileEntity, InventoryHolder, Nameable {
	
	@SuppressWarnings("rawtypes")
	public static final NBTSerializationHandler<AbstractContainerTile>
	NBT_HANDLER = NBTSerializationHandler
					.builder(AbstractContainerTile.class)
					.include(TileEntity.NBT_HANDLER)
					.include(Nameable.NBT_HANDLER)
					.typeArraySearchByteIndexField("Items", "Slot", AbstractContainerTile::hasInventory, (value) -> { return value.getInventory().getContentsUnsafe(); }, ItemStack.NBT_HANDLER)
					.chat("Lock", AbstractContainerTile::getLock, AbstractContainerTile::setLock)
					.build();
	
	/**
	 * Returns the {@link Inventory} of this Tile (creates a Inventory of not present)
	 * @return the Inventory of this Tile
	 */
	I getInventory();
	
	/**
	 * 
	 * @return true if a inventory has been set
	 */
	boolean hasInventory();

	void setLock(Chat lock);
	
	boolean hasLock();
	
	Chat getLock();
	
	@SuppressWarnings("rawtypes")
	@Override
	default NBTSerializationHandler<? extends AbstractContainerTile> getNBTHandler() {
		return NBT_HANDLER;
	}
	
}
