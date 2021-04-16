package de.atlasmc.inventory.meta;

import java.util.List;

import de.atlasmc.recipe.Recipe;

public interface KnowledgeBookMeta extends ItemMeta {
	
	public void addRecipe(Recipe... recipes);
	public KnowledgeBookMeta clone();
	public List<Recipe> getRecipes();
	public boolean hasRecipes();
	public void setRecipes(List<Recipe> recipes);

}
