package de.atlasmc.node.recipe;

import de.atlasmc.NamespacedKey;

public class SmokerRecipe extends AbstractCookingRecipe {

	public SmokerRecipe(NamespacedKey key, NamespacedKey group, RecipeCategory category) {
		super(key, group, category);
	}
	
	@Override
	public RecipeType getType() {
		return RecipeType.SMOKING;
	}

}
