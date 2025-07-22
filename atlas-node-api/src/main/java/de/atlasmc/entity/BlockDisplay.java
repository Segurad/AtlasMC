package de.atlasmc.entity;

import de.atlasmc.block.BlockType;
import de.atlasmc.block.data.BlockData;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface BlockDisplay extends Display {
	
	public static final NBTSerializationHandler<BlockDisplay>
	NBT_HANDLER = NBTSerializationHandler
					.builder(BlockDisplay.class)
					.include(Display.NBT_HANDLER)
					.typeCompoundField("block_state", BlockDisplay::getBlockData, BlockDisplay::setBlockData, BlockData.NBT_HANDLER)
					.build();
	
	BlockData getBlockData();
	
	void setBlockData(BlockData data);

	void setBlockDataType(BlockType type);
	
	BlockType getBlockDataType();
	
	@Override
	default NBTSerializationHandler<? extends BlockDisplay> getNBTHandler() {
		return NBT_HANDLER;
	}

}
