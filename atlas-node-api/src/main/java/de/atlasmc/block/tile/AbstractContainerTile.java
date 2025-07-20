package de.atlasmc.block.tile;

import de.atlasmc.Nameable;
import de.atlasmc.inventory.Inventory;
import de.atlasmc.inventory.InventoryHolder;
import de.atlasmc.inventory.ItemPredicate;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface AbstractContainerTile<I extends Inventory> extends TileEntity, InventoryHolder, Nameable {
	
	@SuppressWarnings("rawtypes")
	public static final NBTSerializationHandler<AbstractContainerTile>
	NBT_HANDLER = NBTSerializationHandler
					.builder(AbstractContainerTile.class)
					.include(TileEntity.NBT_HANDLER)
					.include(Nameable.NBT_HANDLER)
					.include(InventoryHolder.NBT_HANDLER)
					.typeComponentField("lock", AbstractContainerTile::getLock, AbstractContainerTile::setLock, ItemPredicate.NBT_HANDLER)
					.build();
	
	/**
	 * Returns the {@link Inventory} of this Tile (creates a Inventory of not present)
	 * @return the Inventory of this Tile
	 */
	I getInventory();

	void setLock(ItemPredicate lock);
	
	boolean hasLock();
	
	ItemPredicate getLock();
	
	@SuppressWarnings("rawtypes")
	@Override
	default NBTSerializationHandler<? extends AbstractContainerTile> getNBTHandler() {
		return NBT_HANDLER;
	}
	
}
