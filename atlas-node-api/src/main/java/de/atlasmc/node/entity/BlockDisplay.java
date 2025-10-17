package de.atlasmc.node.entity;

import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.block.data.BlockData;
import de.atlasmc.util.nbt.codec.NBTCodec;

public interface BlockDisplay extends Display {
	
	public static final NBTCodec<BlockDisplay>
	NBT_HANDLER = NBTCodec
					.builder(BlockDisplay.class)
					.include(Display.NBT_HANDLER)
					.typeCompoundField("block_state", BlockDisplay::getBlockData, BlockDisplay::setBlockData, BlockData.NBT_HANDLER)
					.build();
	
	BlockData getBlockData();
	
	void setBlockData(BlockData data);

	void setBlockDataType(BlockType type);
	
	BlockType getBlockDataType();
	
	@Override
	default NBTCodec<? extends BlockDisplay> getNBTCodec() {
		return NBT_HANDLER;
	}

}
