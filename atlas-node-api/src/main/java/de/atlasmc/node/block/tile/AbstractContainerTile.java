package de.atlasmc.node.block.tile;

import de.atlasmc.node.Nameable;
import de.atlasmc.node.inventory.Inventory;
import de.atlasmc.node.inventory.InventoryHolder;
import de.atlasmc.node.inventory.ItemPredicate;
import de.atlasmc.util.nbt.codec.NBTCodec;

public interface AbstractContainerTile<I extends Inventory> extends TileEntity, InventoryHolder, Nameable {
	
	@SuppressWarnings("rawtypes")
	public static final NBTCodec<AbstractContainerTile>
	NBT_HANDLER = NBTCodec
					.builder(AbstractContainerTile.class)
					.include(TileEntity.NBT_HANDLER)
					.include(Nameable.NBT_HANDLER)
					.include(InventoryHolder.NBT_HANDLER)
					.typeCompoundField("lock", AbstractContainerTile::getLock, AbstractContainerTile::setLock, ItemPredicate.NBT_HANDLER)
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
	default NBTCodec<? extends AbstractContainerTile> getNBTCodec() {
		return NBT_HANDLER;
	}
	
}
