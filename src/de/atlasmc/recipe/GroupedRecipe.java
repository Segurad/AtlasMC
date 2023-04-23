package de.atlasmc.recipe;

import de.atlasmc.NamespacedKey;

public abstract class GroupedRecipe extends Recipe {

	private final NamespacedKey group;
	
	public GroupedRecipe(NamespacedKey key, NamespacedKey group) {
		super(key);
		this.group = group;
	}
	
	public NamespacedKey getGroup() {
		return group;
	}

}
