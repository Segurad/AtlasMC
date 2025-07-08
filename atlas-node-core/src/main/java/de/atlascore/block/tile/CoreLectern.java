package de.atlascore.block.tile;

import de.atlasmc.block.BlockType;
import de.atlasmc.block.tile.Lectern;
import de.atlasmc.inventory.ContainerFactory;
import de.atlasmc.inventory.InventoryType;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.inventory.LecternInventory;

public class CoreLectern extends CoreTileEntity implements Lectern {
	
	private LecternInventory inv;
	
	public CoreLectern(BlockType type) {
		super(type);
	}

	@Override
	public ItemStack getBook() {
		if (inv == null) 
			return null;
		return getInventory().getBook();
	}

	@Override
	public void setBook(ItemStack book) {
		getInventory().setBook(book);
	}

	@Override
	public int getPage() {
		if (inv == null) 
			return 0;
		return getInventory().getPage();
	}

	@Override
	public void setPage(int page) {
		getInventory().setPage(page);
	}

	@Override
	public LecternInventory getInventory() {
		if (inv == null) 
			inv = ContainerFactory.LECTERN_INV_FACTORY.create(InventoryType.LECTERN, this);
		return inv;
	}

}
