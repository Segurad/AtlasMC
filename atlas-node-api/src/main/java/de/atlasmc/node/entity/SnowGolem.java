package de.atlasmc.node.entity;

import de.atlasmc.nbt.codec.NBTCodec;

public interface SnowGolem extends AbstractGolem {
	
	public static final NBTCodec<SnowGolem>
	NBT_HANDLER = NBTCodec
					.builder(SnowGolem.class)
					.include(AbstractGolem.NBT_HANDLER)
					.boolField("Pumpkin", SnowGolem::hasPumpkinHat, SnowGolem::setPumkinHat, true)
					.build();
	
	boolean hasPumpkinHat();
	
	void setPumkinHat(boolean hat);

	@Override
	default NBTCodec<? extends SnowGolem> getNBTCodec() {
		return NBT_HANDLER;
	}
	
}
