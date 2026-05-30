package de.atlasmc.node.block.tile;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.node.inventory.InventoryHolder;
import de.atlasmc.node.inventory.ItemStack;
import de.atlasmc.node.inventory.LecternInventory;
import de.atlasmc.util.annotation.NotNull;

public interface Lectern extends TileEntity, InventoryHolder {
	
	@NotNull
	public static final NBTCodec<Lectern>
	NBT_CODEC = NBTCodec
					.builder(Lectern.class)
					.include(TileEntity.NBT_CODEC)
					.codec("Book", Lectern::getBook, Lectern::setBook, ItemStack.NBT_CODEC)
					.intField("Page", Lectern::getPage, Lectern::setPage)
					.build();
	
	@NotNull
	@Override
	LecternInventory getInventory();

	ItemStack getBook();
	
	void setBook(ItemStack book);

	int getPage();
	
	void setPage(int page);
	
	@Override
	default NBTCodec<? extends Lectern> getNBTCodec() {
		return NBT_CODEC;
	}
	
}
