package de.atlasmc.node.block.data;

import java.util.List;

import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.block.data.property.BlockDataProperty;
import de.atlasmc.util.nbt.serialization.NBTSerializable;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface BlockData extends Cloneable, NBTSerializable {

	public static final NBTSerializationHandler<BlockData>
	NBT_HANDLER = NBTSerializationHandler
					.builder(BlockData.class)
					.searchKeyConstructor("Name", BlockType.REGISTRY_KEY, BlockType::createBlockData, BlockData::getType)
					.addField(BlockDataProperty.getBlockDataPropertiesField("Properties"))
					.build();
	
	BlockData clone();
	
	BlockType getType();
	
	int getStateID();

	List<BlockDataProperty<?>> getProperties();
	
	@Override
	default NBTSerializationHandler<? extends BlockData> getNBTHandler() {
		return NBT_HANDLER;
	}
	
}
