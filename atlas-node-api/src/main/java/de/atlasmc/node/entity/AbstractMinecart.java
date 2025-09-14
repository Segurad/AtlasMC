package de.atlasmc.node.entity;

import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.block.data.BlockData;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface AbstractMinecart extends Vehicle {
	
	public static final NBTSerializationHandler<AbstractMinecart>
	NBT_HANDLER = NBTSerializationHandler
					.builder(AbstractMinecart.class)
					.include(Vehicle.NBT_HANDLER)
					.intField("DisplayOffset", AbstractMinecart::getCustomBlockY, AbstractMinecart::setCustomBlockY)
					.typeCompoundField("DisplayState", AbstractMinecart::getCustomBlock, AbstractMinecart::setCustomBlock, BlockData.NBT_HANDLER)
					.build();
	
	BlockData getCustomBlock();
	
	boolean hasCustomBlock();
	
	void setCustomBlock(BlockData data);
	
	void setCustomBlockType(BlockType type);
	
	int getCustomBlockY();
	
	void setCustomBlockY(int y);
	
	boolean getShowCustomBlock();

	void setShowCustomBlock(boolean show);
	
	@Override
	default NBTSerializationHandler<? extends AbstractMinecart> getNBTHandler() {
		return NBT_HANDLER;
	}
	
}
