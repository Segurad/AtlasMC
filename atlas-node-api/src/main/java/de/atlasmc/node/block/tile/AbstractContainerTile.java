package de.atlasmc.node.block.tile;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.node.Nameable;
import de.atlasmc.node.inventory.Inventory;
import de.atlasmc.node.inventory.InventoryHolder;
import de.atlasmc.node.inventory.ItemPredicate;
import de.atlasmc.util.annotation.NotNull;

public interface AbstractContainerTile<I extends Inventory> extends TileEntity, InventoryHolder, Nameable {
	
	@NotNull
	@SuppressWarnings("rawtypes")
	public static final NBTCodec<AbstractContainerTile>
	NBT_CODEC = NBTCodec
					.builder(AbstractContainerTile.class)
					.include(TileEntity.NBT_CODEC)
					.include(Nameable.NBT_CODEC)
					.include(InventoryHolder.NBT_CODEC)
					.codec("lock", AbstractContainerTile::getLock, AbstractContainerTile::setLock, ItemPredicate.NBT_CODEC)
					.build();
	
	/**
	 * Returns the {@link Inventory} of this Tile (creates a Inventory of not present)
	 * @return the Inventory of this Tile
	 */
	@Override
	@NotNull
	I getInventory();

	void setLock(ItemPredicate lock);
	
	boolean hasLock();
	
	ItemPredicate getLock();
	
	@SuppressWarnings("rawtypes")
	@Override
	default NBTCodec<? extends AbstractContainerTile> getNBTCodec() {
		return NBT_CODEC;
	}
	
}
