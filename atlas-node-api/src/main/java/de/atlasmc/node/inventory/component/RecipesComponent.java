package de.atlasmc.node.inventory.component;

import java.util.List;

import de.atlasmc.NamespacedKey;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.util.annotation.NotNull;

public interface RecipesComponent extends ItemComponent {
	
	@NotNull
	public static final NBTCodec<RecipesComponent>
	NBT_CODEC = NBTCodec
					.builder(RecipesComponent.class)
					.include(ItemComponent.NBT_CODEC)
					.codecList(ComponentType.RECIPES.getNamespacedKey(), RecipesComponent::hasRecipes, RecipesComponent::getRecipes, NamespacedKey.NBT_CODEC)
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
	
	@Override
	RecipesComponent clone();
	
	@Override
	default NBTCodec<? extends RecipesComponent> getNBTCodec() {
		return NBT_CODEC;
	}

}
