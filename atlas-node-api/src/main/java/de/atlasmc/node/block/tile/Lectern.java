package de.atlasmc.node.block.tile;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.node.inventory.InventoryHolder;
import de.atlasmc.node.inventory.ItemStack;
import de.atlasmc.node.inventory.LecternInventory;

public interface Lectern extends TileEntity, InventoryHolder {
	
	public static final NBTCodec<Lectern>
	NBT_HANDLER = NBTCodec
					.builder(Lectern.class)
					.include(TileEntity.NBT_HANDLER)
					.codec("Book", Lectern::getBook, Lectern::setBook, ItemStack.NBT_CODEC)
					.intField("Page", Lectern::getPage, Lectern::setPage)
					.build();
	
	LecternInventory getInventory();

	ItemStack getBook();
	
	void setBook(ItemStack book);

	int getPage();
	
	void setPage(int page);
	
	@Override
	default NBTCodec<? extends Lectern> getNBTCodec() {
		return NBT_HANDLER;
	}
	
}
