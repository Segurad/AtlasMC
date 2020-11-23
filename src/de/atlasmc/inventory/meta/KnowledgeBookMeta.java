package de.atlasmc.inventory.meta;

import java.util.List;

public interface KnowledgeBookMeta extends ItemMeta {
	
	public void addRecipe(String... recipes);
	public KnowledgeBookMeta clone();
	public List<String> getRecipes();
	public boolean hasRecipes();
	public void setRecipes(List<String> recipes);

}
