package de.atlasmc.inventory.itempredicate;

import de.atlasmc.Material;
import de.atlasmc.inventory.ItemStack;

public interface ItemPredicate {

	boolean match(ItemStack item);
	
	boolean match(Material material);
	
}
