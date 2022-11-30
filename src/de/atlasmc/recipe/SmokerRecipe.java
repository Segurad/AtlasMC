package de.atlasmc.recipe;

import de.atlasmc.NamespacedKey;

public class SmokerRecipe extends AbstractCookingRecipe {

	public SmokerRecipe(NamespacedKey key) {
		super(key);
	}
	
	@Override
	public RecipeType getType() {
		return RecipeType.SMOKING;
	}

}
