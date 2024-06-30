package de.atlasmc.recipe;

import de.atlasmc.NamespacedKey;

public abstract class AbstractSmithingRecipe extends Recipe {

	private Ingredient templateIngrent;
	private Ingredient baseIngredient;
	private Ingredient additionalIngredient;
	
	public AbstractSmithingRecipe(NamespacedKey key) {
		super(key, RecipeCategory.EQUIPMENT);
	}
	
	public Ingredient getTemplateIngrent() {
		return templateIngrent;
	}
	
	public void setTemplateIngrent(Ingredient templateIngrent) {
		this.templateIngrent = templateIngrent;
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

}
