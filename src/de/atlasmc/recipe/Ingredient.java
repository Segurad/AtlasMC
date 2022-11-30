package de.atlasmc.recipe;

import java.util.List;

import de.atlasmc.inventory.ItemStack;

/**
 * Represents a ingredient of a {@link Recipe}
 */
public interface Ingredient {
	
	/**
	 * Returns the list of usable items for this ingredient
	 * @return list
	 */
	public List<ItemStack> getUseableItems();
	
	/**
	 * Returns a usable item of this ingredient.
	 * By default the first item of all usable items 
	 * @return item
	 */
	public ItemStack getItem();
	
	/**
	 * Tests if this item is compatible with this ingredient
	 * @param item
	 * @return true if test successfully
	 */
	public boolean test(ItemStack item);

}
