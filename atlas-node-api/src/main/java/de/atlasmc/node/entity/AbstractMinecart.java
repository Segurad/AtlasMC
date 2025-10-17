package de.atlasmc.node.entity;

import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.block.data.BlockData;
import de.atlasmc.util.nbt.codec.NBTCodec;

public interface AbstractMinecart extends Vehicle {
	
	public static final NBTCodec<AbstractMinecart>
	NBT_HANDLER = NBTCodec
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
	default NBTCodec<? extends AbstractMinecart> getNBTCodec() {
		return NBT_HANDLER;
	}
	
}
