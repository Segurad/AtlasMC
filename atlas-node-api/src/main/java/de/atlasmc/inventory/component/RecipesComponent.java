package de.atlasmc.inventory.component;

import java.util.List;

import de.atlasmc.NamespacedKey;

public interface RecipesComponent extends ItemComponent {
	
	public static final NamespacedKey COMPONENT_KEY = NamespacedKey.literal("minecraft:recipes");
	
	List<NamespacedKey> getRecipes();
	
	boolean hasRecipes();
	
	void addRecipe(NamespacedKey recipe);
	
	void removeRecipe(NamespacedKey recipe);
	
	RecipesComponent clone();

}
