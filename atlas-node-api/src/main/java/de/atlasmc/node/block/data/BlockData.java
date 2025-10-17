package de.atlasmc.node.block.data;

import java.util.List;

import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.block.data.property.BlockDataProperty;
import de.atlasmc.util.nbt.codec.NBTSerializable;
import de.atlasmc.util.nbt.codec.NBTCodec;

public interface BlockData extends Cloneable, NBTSerializable {

	public static final NBTCodec<BlockData>
	NBT_HANDLER = NBTCodec
					.builder(BlockData.class)
					.searchKeyConstructor("Name", BlockType.REGISTRY_KEY, BlockType::createBlockData, BlockData::getType)
					.addField(BlockDataProperty.getBlockDataPropertiesField("Properties"))
					.build();
	
	BlockData clone();
	
	BlockType getType();
	
	int getStateID();

	List<BlockDataProperty<?>> getProperties();
	
	@Override
	default NBTCodec<? extends BlockData> getNBTCodec() {
		return NBT_HANDLER;
	}
	
}
