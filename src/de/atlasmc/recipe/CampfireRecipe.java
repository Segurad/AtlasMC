package de.atlasmc.recipe;

import de.atlasmc.NamespacedKey;

public class CampfireRecipe extends AbstractCookingRecipe {

	public CampfireRecipe(NamespacedKey key, NamespacedKey group) {
		super(key, group);
	}
	
	@Override
	public RecipeType getType() {
		return RecipeType.CAMPFIRE_COOKING;
	}

}
