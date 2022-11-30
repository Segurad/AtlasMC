package de.atlasmc.recipe;

import java.util.ArrayList;
import java.util.List;

import de.atlasmc.Material;
import de.atlasmc.inventory.ItemStack;

/**
 * Ingredient based only on {@link Material} only
 */
public class MaterialIngredient implements Ingredient {

	private List<Material> mats;
	
	public MaterialIngredient(List<Material> mats) {
		this.mats = mats;
	}
	
	@Override
	public List<ItemStack> getUseableItems() {
		if (mats == null || mats.isEmpty())
			return List.of();
		List<ItemStack> items = new ArrayList<>(mats.size());
		for (Material mat : mats)
			items.add(new ItemStack(mat));
		return items;
	}

	@Override
	public ItemStack getItem() {
		if (mats == null || mats.isEmpty())
			return null;
		return new ItemStack(mats.get(0));
	}

	@Override
	public boolean test(ItemStack item) {
		return mats.contains(item.getType());
	}

}
