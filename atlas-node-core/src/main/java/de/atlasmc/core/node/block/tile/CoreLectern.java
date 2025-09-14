package de.atlasmc.core.node.block.tile;

import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.block.tile.Lectern;
import de.atlasmc.node.inventory.ContainerFactory;
import de.atlasmc.node.inventory.InventoryType;
import de.atlasmc.node.inventory.ItemStack;
import de.atlasmc.node.inventory.LecternInventory;

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
	
	@Override
	public boolean hasInventory() {
		return inv != null;
	}

}
