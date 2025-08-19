package de.atlascore.inventory.component;

import java.util.ArrayList;
import java.util.List;

import de.atlasmc.NamespacedKey;
import de.atlasmc.inventory.component.AbstractItemComponent;
import de.atlasmc.inventory.component.ComponentType;
import de.atlasmc.inventory.component.RecipesComponent;

public class CoreRecipesComponent extends AbstractItemComponent implements RecipesComponent {

	private List<NamespacedKey> recipes;
	
	public CoreRecipesComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public CoreRecipesComponent clone() {
		CoreRecipesComponent clone = (CoreRecipesComponent) super.clone();
		if (hasRecipes()) {
			clone.recipes = new ArrayList<>(recipes);
		}
		return clone;
	}

	@Override
	public List<NamespacedKey> getRecipes() {
		if (recipes == null)
			recipes = new ArrayList<>();
		return recipes;
	}

	@Override
	public boolean hasRecipes() {
		return recipes != null && !recipes.isEmpty();
	}

}
