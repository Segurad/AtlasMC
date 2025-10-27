package de.atlasmc.node.inventory.component;

import java.util.List;

import de.atlasmc.NamespacedKey;
import de.atlasmc.util.nbt.codec.NBTCodec;

public interface RecipesComponent extends ItemComponent {
	
	public static final NBTCodec<RecipesComponent>
	NBT_HANDLER = NBTCodec
					.builder(RecipesComponent.class)
					.include(ItemComponent.NBT_CODEC)
					.namespacedKeyListField(ComponentType.RECIPES.getNamespacedKey(), RecipesComponent::hasRecipes, RecipesComponent::getRecipes)
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
	default NBTCodec<? extends RecipesComponent> getNBTCodec() {
		return NBT_HANDLER;
	}

}
