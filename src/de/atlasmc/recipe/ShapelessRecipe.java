package de.atlasmc.recipe;

import java.util.ArrayList;
import java.util.List;

import de.atlasmc.NamespacedKey;

public class ShapelessRecipe extends Recipe {

	private List<Ingredient> ingredients;
	
	public ShapelessRecipe(NamespacedKey key) {
		super(key);
	}
	
	public List<Ingredient> getIngredients() {
		if (ingredients == null)
			ingredients = new ArrayList<>(1);
		return ingredients;
	}
	
	public void addIngredient(Ingredient ingredient) {
		this.ingredients.add(ingredient);
	}
	
	public void setIngredients(List<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}

	@Override
	public RecipeType getType() {
		return RecipeType.CRAFTING_SHAPELESS;
	}
	
}
