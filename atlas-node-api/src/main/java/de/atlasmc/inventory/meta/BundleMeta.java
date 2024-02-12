package de.atlasmc.inventory.meta;

import java.util.List;

import de.atlasmc.inventory.ItemStack;

public interface BundleMeta extends ItemMeta {
	
	void addItem(ItemStack item);
	
	List<ItemStack> getItems();
	
	boolean hasItems();
	
	void setItems(List<ItemStack> items);
	
	BundleMeta clone();

}
