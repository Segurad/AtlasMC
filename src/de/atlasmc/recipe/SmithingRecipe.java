package de.atlasmc.recipe;

import de.atlasmc.NamespacedKey;

public class SmithingRecipe extends Recipe {

	private Ingredient baseIngredient, additionalIngredient;
	
	public SmithingRecipe(NamespacedKey key) {
		super(key);
	}
	
	public Ingredient getBaseIngredient() {
		return baseIngredient;
	}
	
	public Ingredient getAdditionalIngredient() {
		return additionalIngredient;
	}
	
	public void setBaseIngredient(Ingredient baseIngredient) {
		this.baseIngredient = baseIngredient;
	}
	
	public void setAdditionalIngredient(Ingredient additionalIngredient) {
		this.additionalIngredient = additionalIngredient;
	}
	
	@Override
	public RecipeType getType() {
		return RecipeType.SMITHING;
	}

}
