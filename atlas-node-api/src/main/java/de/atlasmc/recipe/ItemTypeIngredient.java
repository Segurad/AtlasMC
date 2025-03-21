package de.atlasmc.recipe;

import java.util.ArrayList;
import java.util.List;

import de.atlasmc.inventory.ItemStack;
import de.atlasmc.inventory.ItemType;
import de.atlasmc.util.dataset.DataSet;

/**
 * Ingredient based only on {@link Material} only
 */
public class ItemTypeIngredient implements Ingredient {

	private DataSet<ItemType> types;
	
	public ItemTypeIngredient(DataSet<ItemType> mats) {
		this.types = mats;
	}
	
	@Override
	public List<ItemStack> getUseableItems() {
		if (types == null || types.isEmpty())
			return List.of();
		List<ItemStack> items = new ArrayList<>(types.size());
		for (ItemType type : types.values())
			items.add(new ItemStack(type));
		return items;
	}

	@Override
	public ItemStack getItem() {
		if (types == null || types.isEmpty())
			return null;
		return new ItemStack(types.values().iterator().next());
	}

	@Override
	public boolean test(ItemStack item) {
		return types.contains(item.getType());
	}

}
