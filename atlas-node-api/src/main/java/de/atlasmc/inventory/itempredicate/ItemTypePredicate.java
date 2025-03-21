package de.atlasmc.inventory.itempredicate;

import de.atlasmc.inventory.ItemStack;
import de.atlasmc.inventory.ItemType;
import de.atlasmc.util.dataset.DataSet;

public class ItemTypePredicate implements ItemPredicate {
	
	private DataSet<ItemType> types;
	
	public ItemTypePredicate(DataSet<ItemType> types) {
		this.types = types;
	}

	@Override
	public boolean match(ItemStack item) {
		return types.contains(item.getType());
	}

	@Override
	public boolean match(ItemType type) {
		return types.contains(type);
	}

}
