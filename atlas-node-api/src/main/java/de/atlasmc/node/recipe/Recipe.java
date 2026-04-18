package de.atlasmc.node.recipe;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.nbt.codec.NBTSerializable;
import de.atlasmc.node.recipe.display.RecipeDisplay;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.annotation.Nullable;

public interface Recipe extends NBTSerializable {
	
	public static final NBTCodec<Recipe>
	NBT_CODEC = NBTCodec.builder(Recipe.class)
				.searchKeyEnumConstructor("type", RecipeType.class, RecipeType::createRecipe, Recipe::getType)
				.build();
	
	@NotNull
	RecipeType getType();
	
	RecipeDisplay getDisplay();
	
	@NotNull
	RecipeCategory getCategory();
	
	@Nullable
	String getGroup();
	
	@Override
	default NBTCodec<? extends NBTSerializable> getNBTCodec() {
		return NBT_CODEC;
	}
	
}
