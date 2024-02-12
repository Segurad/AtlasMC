package de.atlasmc.recipe;

import de.atlasmc.NamespacedKey;

public class BlastFurnaceRecipe extends AbstractCookingRecipe {

	public BlastFurnaceRecipe(NamespacedKey key, NamespacedKey group, RecipeCategory category) {
		super(key, group, category);
	}

	@Override
	public RecipeType getType() {
		return RecipeType.BLASTING;
	}

}
