package de.atlasmc.node.recipe;

import de.atlasmc.NamespacedKey;

public class SmithingTrimRecipe extends AbstractSmithingRecipe {

	public SmithingTrimRecipe(NamespacedKey key) {
		super(key);
	}

	@Override
	public RecipeType getType() {
		return RecipeType.SMITHING_TRIM;
	}

}
