package de.atlasmc.recipe;

import de.atlasmc.NamespacedKey;

public class FurnaceRecipe extends AbstractCookingRecipe {

	public FurnaceRecipe(NamespacedKey key, NamespacedKey group, RecipeCategory category) {
		super(key, group, category);
	}
	
	@Override
	public RecipeType getType() {
		return RecipeType.SMELTING;
	}

}
