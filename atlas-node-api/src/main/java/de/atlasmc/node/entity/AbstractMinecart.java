package de.atlasmc.node.entity;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.block.data.BlockData;
import de.atlasmc.util.annotation.NotNull;

public interface AbstractMinecart extends Vehicle {
	
	@NotNull
	public static final NBTCodec<AbstractMinecart>
	NBT_CODEC = NBTCodec
					.builder(AbstractMinecart.class)
					.include(Vehicle.NBT_CODEC)
					.intField("DisplayOffset", AbstractMinecart::getCustomBlockY, AbstractMinecart::setCustomBlockY)
					.codec("DisplayState", AbstractMinecart::getCustomBlock, AbstractMinecart::setCustomBlock, BlockData.NBT_CODEC)
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
		return NBT_CODEC;
	}
	
}
