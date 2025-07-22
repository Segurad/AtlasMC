package de.atlasmc.entity;

import de.atlasmc.block.BlockType;
import de.atlasmc.block.data.BlockData;
import de.atlasmc.entity.data.AngerableMob;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface Enderman extends Monster, AngerableMob {
	
	public static final NBTSerializationHandler<Enderman>
	NBT_HANDLER = NBTSerializationHandler
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
	default NBTSerializationHandler<? extends Enderman> getNBTHandler() {
		return NBT_HANDLER;
	}
	
}
