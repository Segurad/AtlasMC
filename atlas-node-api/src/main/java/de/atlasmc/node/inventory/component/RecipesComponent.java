package de.atlasmc.node.inventory.component;

import java.util.List;
import java.util.Objects;

import de.atlasmc.NamespacedKey;
import de.atlasmc.nbt.codec.CodecListFieldCodec;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.util.annotation.NotNull;

public interface RecipesComponent extends ItemComponent {
	
	@NotNull
	public static final NBTCodec<RecipesComponent>
	NBT_CODEC = CodecListFieldCodec
				.builder(RecipesComponent.class, NamespacedKey.NBT_CODEC)
				.include(ItemComponent.NBT_CODEC)
				.hasData(RecipesComponent::hasRecipes)
				.getter(RecipesComponent::getRecipes)
				.build();
	
	List<NamespacedKey> getRecipes();
	
	boolean hasRecipes();
	
	default boolean addRecipe(NamespacedKey recipe) {
		Objects.requireNonNull(recipe, "recipe");
		return getRecipes().add(recipe);
	}
	
	default boolean removeRecipe(NamespacedKey recipe) {
		Objects.requireNonNull(recipe, "recipe");
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
