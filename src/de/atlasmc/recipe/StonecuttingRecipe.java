package de.atlasmc.recipe;

import de.atlasmc.NamespacedKey;

public class StonecuttingRecipe extends GroupedRecipe {

	private Ingredient ingredient;
	
	public StonecuttingRecipe(NamespacedKey key, NamespacedKey group) {
		super(key, group);
	}
	
	public Ingredient getIngredient() {
		return ingredient;
	}
	
	public void setIngredient(Ingredient ingredient) {
		this.ingredient = ingredient;
	}
	
	@Override
	public RecipeType getType() {
		return RecipeType.STONECUTTING;
	}

}
