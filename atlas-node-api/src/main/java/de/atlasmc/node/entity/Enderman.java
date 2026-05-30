package de.atlasmc.node.entity;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.block.data.BlockData;
import de.atlasmc.util.annotation.NotNull;

public interface Enderman extends Monster, AngerableMob {
	
	@NotNull
	public static final NBTCodec<Enderman>
	NBT_CODEC = NBTCodec
					.builder(Enderman.class)
					.include(Monster.NBT_CODEC)
					.codec("carriedBlockState", Enderman::getCarriedBlock, Enderman::setCarriedBlock, BlockData.NBT_CODEC)
					.build();

	BlockType getCarriedBlockType();
	
	BlockData getCarriedBlock();
	
	void setCarriedBlock(BlockData data);
	
	void setCarriedBlockType(BlockType type);
	
	/**
	 * Sets the carried block as changed for the next update
	 */
	void setCarriedBlockChanged();
	
	boolean hasCarriedBlock();
	
	void setScreaming(boolean screaming);
	
	boolean isScreaming();
	
	void setStaring(boolean staring);
	
	boolean isStaring();
	
	@Override
	default NBTCodec<? extends Enderman> getNBTCodec() {
		return NBT_CODEC;
	}
	
}
