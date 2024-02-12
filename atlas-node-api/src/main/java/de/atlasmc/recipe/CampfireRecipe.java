package de.atlasmc.recipe;

import de.atlasmc.NamespacedKey;

public class CampfireRecipe extends AbstractCookingRecipe {

	public CampfireRecipe(NamespacedKey key, NamespacedKey group, RecipeCategory category) {
		super(key, group, category);
	}
	
	@Override
	public RecipeType getType() {
		return RecipeType.CAMPFIRE_COOKING;
	}

}
