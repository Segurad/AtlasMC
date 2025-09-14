package de.atlasmc.node.recipe;

import de.atlasmc.NamespacedKey;

public class SmithingTransformRecipe extends AbstractSmithingRecipe {

	public SmithingTransformRecipe(NamespacedKey key) {
		super(key);
	}

	@Override
	public RecipeType getType() {
		return RecipeType.SMITHING_TRANSFORM;
	}

}
