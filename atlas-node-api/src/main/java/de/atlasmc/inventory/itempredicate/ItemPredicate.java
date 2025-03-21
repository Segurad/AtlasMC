package de.atlasmc.inventory.itempredicate;

import de.atlasmc.inventory.ItemStack;
import de.atlasmc.inventory.ItemType;

public interface ItemPredicate {

	boolean match(ItemStack item);
	
	boolean match(ItemType type);
	
}
