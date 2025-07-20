package de.atlasmc.block.tile;

import de.atlasmc.inventory.InventoryHolder;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.inventory.LecternInventory;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface Lectern extends TileEntity, InventoryHolder {
	
	public static final NBTSerializationHandler<Lectern>
	NBT_HANDLER = NBTSerializationHandler
					.builder(Lectern.class)
					.include(TileEntity.NBT_HANDLER)
					.typeComponentField("Book", Lectern::getBook, Lectern::setBook, ItemStack.NBT_HANDLER)
					.intField("Page", Lectern::getPage, Lectern::setPage)
					.build();
	
	LecternInventory getInventory();

	ItemStack getBook();
	
	void setBook(ItemStack book);

	int getPage();
	
	void setPage(int page);
	
	@Override
	default NBTSerializationHandler<? extends Lectern> getNBTHandler() {
		return NBT_HANDLER;
	}
	
}
