package de.atlasmc.recipe;

import java.util.HashMap;
import java.util.Map;

import de.atlasmc.NamespacedKey;

public class ShapedRecipe extends GroupedRecipe {
	
	private Map<Character, Ingredient> ingredients;
	private String[] shape;
	
	public ShapedRecipe(NamespacedKey key, NamespacedKey group) {
		super(key, group);
		ingredients = new HashMap<>(1);
	}
	
	public Ingredient getIngredient(char key) {
		return ingredients.get(key);
	}
	
	public Map<Character, Ingredient> getIngredients() {
		return ingredients;
	}
	
	public void setIngredient(char key, Ingredient ingredient) {
		if (ingredient == null)
			throw new IllegalArgumentException("Ingredient can not be null!");
		ingredients.put(key, ingredient);
	}
	
	public String[] getShape() {
		return shape;
	}

	public void setShape(String... shape) {
		if (shape == null || shape.length == 0) {
			this.shape = null;
			return;
		}
		final int width = shape[0].length();
		for (String s : shape)
			if (s.length() != width)
				throw new IllegalArgumentException("All lines must have the same length!");
		this.shape = shape;
	}
	
	@Override
	public RecipeType getType() {
		return RecipeType.CRAFTING_SHAPED;
	}

	public int getShapeWidth() {
		return shape == null ? 0 : shape[0].length();
	}

	public int getShapeHeight() {
		return shape == null ? 0 : shape.length;
	}
	
}
