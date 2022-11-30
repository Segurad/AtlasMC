package de.atlasmc.recipe;

import de.atlasmc.NamespacedKey;

public class FurnaceRecipe extends AbstractCookingRecipe {

	public FurnaceRecipe(NamespacedKey key) {
		super(key);
	}
	
	@Override
	public RecipeType getType() {
		return RecipeType.SMELTING;
	}

}
