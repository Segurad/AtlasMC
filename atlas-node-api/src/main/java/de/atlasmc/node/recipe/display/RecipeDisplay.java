package de.atlasmc.node.recipe.display;

import de.atlasmc.io.codec.StreamCodec;
import de.atlasmc.io.codec.StreamSerializable;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.nbt.codec.NBTSerializable;

public interface RecipeDisplay extends NBTSerializable, StreamSerializable {
	
	public static final StreamCodec<RecipeDisplay> 
	STREAM_CODEC = StreamCodec.builder(RecipeDisplay.class)
					.enumVarIntConstructor(DisplayType.class, DisplayType::createDisplay, RecipeDisplay::getType)
					.build();
	
	public static final NBTCodec<RecipeDisplay>
	NBT_CODEC = NBTCodec.builder(RecipeDisplay.class)
				.searchKeyEnumConstructor("id", DisplayType.class, DisplayType::createDisplay, RecipeDisplay::getType)
				.build();
	
	DisplayType getType();
	
	@Override
	default StreamCodec<? extends RecipeDisplay> getStreamCodec() {
		return STREAM_CODEC;
	}
	
	@Override
	default NBTCodec<? extends NBTSerializable> getNBTCodec() {
		return NBT_CODEC;
	}

}
