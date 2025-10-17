package de.atlasmc.node.entity;

import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.block.data.BlockData;
import de.atlasmc.util.nbt.codec.NBTCodec;

public interface Enderman extends Monster, AngerableMob {
	
	public static final NBTCodec<Enderman>
	NBT_HANDLER = NBTCodec
					.builder(Enderman.class)
					.include(Monster.NBT_HANDLER)
					.typeCompoundField("carriedBlockState", Enderman::getCarriedBlock, Enderman::setCarriedBlock, BlockData.NBT_HANDLER)
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
		return NBT_HANDLER;
	}
	
}
