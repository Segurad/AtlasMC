package de.atlasmc.recipe;

import de.atlasmc.NamespacedKey;

public class BlastFurnaceRecipe extends AbstractCookingRecipe {

	public BlastFurnaceRecipe(NamespacedKey key) {
		super(key);
	}

	@Override
	public RecipeType getType() {
		return RecipeType.BLASTING;
	}

}
