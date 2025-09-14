package de.atlasmc.core.node.inventory;

import java.util.Iterator;

import de.atlasmc.node.inventory.Inventory;
import de.atlasmc.node.inventory.ItemStack;

public class InventoryIterator implements Iterator<ItemStack> {
	
	private final Inventory inv;
	private int index;
	private final int size;
	private final boolean unsafe;
	
	public InventoryIterator(Inventory inv, boolean unsafe) {
		this.inv = inv;
		this.size = inv.getSize();
		this.unsafe = unsafe;
	}
	
	public boolean isUnsafe() {
		return unsafe;
	}

	@Override
	public boolean hasNext() {
		for (int i = index+1; i < size; i++)
			if (inv.getItemUnsafe(i) != null)
				return true;
		return false;
	}

	@Override
	public ItemStack next() {
		index++;
		for (; index < size; index++) {
			ItemStack item = null;
			if (unsafe)
				item = inv.getItemUnsafe(index);
			else
				item = inv.getItem(index);
			if (item != null)
				return item;
		}
		return null;
	}

	@Override
	public void remove() {
		inv.setItem(index, null);
	}
}
