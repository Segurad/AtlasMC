package de.atlasmc.recipe;

import de.atlasmc.NamespacedKey;

public abstract class AbstractCookingRecipe extends GroupedRecipe {

	private Ingredient ingredient;
	private int time;
	private	float xp;
	
	public AbstractCookingRecipe(NamespacedKey key, NamespacedKey group, RecipeCategory category) {
		super(key, group, category);
	}
	
	public Ingredient getIngredient() {
		return ingredient;
	}
	
	public int getTime() {
		return time;
	}
	
	public float getXp() {
		return xp;
	}
	
	public void setIngredient(Ingredient ingredient) {
		this.ingredient = ingredient;
	}
	
	public void setTime(int time) {
		this.time = time;
	}
	
	public void setXp(float xp) {
		this.xp = xp;
	}

}
