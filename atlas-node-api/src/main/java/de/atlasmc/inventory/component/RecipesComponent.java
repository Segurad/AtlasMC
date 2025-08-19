package de.atlasmc.inventory.component;

import java.util.List;

import de.atlasmc.NamespacedKey;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface RecipesComponent extends ItemComponent {
	
	public static final NBTSerializationHandler<RecipesComponent>
	NBT_HANDLER = NBTSerializationHandler
					.builder(RecipesComponent.class)
					.include(ItemComponent.NBT_HANDLER)
					.namespacedKeyListField(ComponentType.RECIPES, RecipesComponent::hasRecipes, RecipesComponent::getRecipes)
					.build();
	
	List<NamespacedKey> getRecipes();
	
	boolean hasRecipes();
	
	default boolean addRecipe(NamespacedKey recipe) {
		if (recipe == null)
			throw new IllegalArgumentException("Recipe can not be null!");
		return getRecipes().add(recipe);
	}
	
	default boolean removeRecipe(NamespacedKey recipe) {
		if (recipe == null)
			throw new IllegalArgumentException("Recipe can not be null!");
		if (hasRecipes())
			return getRecipes().remove(recipe);
		return false;
	}
	
	RecipesComponent clone();
	
	@Override
	default NBTSerializationHandler<? extends RecipesComponent> getNBTHandler() {
		return NBT_HANDLER;
	}

}
