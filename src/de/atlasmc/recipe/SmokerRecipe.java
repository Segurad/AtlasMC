package de.atlasmc.recipe;

import de.atlasmc.NamespacedKey;

public class SmokerRecipe extends AbstractCookingRecipe {

	public SmokerRecipe(NamespacedKey key, NamespacedKey group) {
		super(key, group);
	}
	
	@Override
	public RecipeType getType() {
		return RecipeType.SMOKING;
	}

}
