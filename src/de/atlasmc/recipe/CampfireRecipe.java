package de.atlasmc.recipe;

import de.atlasmc.NamespacedKey;

public class CampfireRecipe extends AbstractCookingRecipe {

	public CampfireRecipe(NamespacedKey key) {
		super(key);
	}
	
	@Override
	public RecipeType getType() {
		return RecipeType.CAMPFIRE_COOKING;
	}

}
