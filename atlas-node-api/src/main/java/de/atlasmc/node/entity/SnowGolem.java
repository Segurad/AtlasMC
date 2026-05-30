package de.atlasmc.node.entity;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.util.annotation.NotNull;

public interface SnowGolem extends AbstractGolem {
	
	@NotNull
	public static final NBTCodec<SnowGolem>
	NBT_CODEC = NBTCodec
					.builder(SnowGolem.class)
					.include(AbstractGolem.NBT_CODEC)
					.boolField("Pumpkin", SnowGolem::hasPumpkinHat, SnowGolem::setPumkinHat, true)
					.build();
	
	boolean hasPumpkinHat();
	
	void setPumkinHat(boolean hat);

	@Override
	default NBTCodec<? extends SnowGolem> getNBTCodec() {
		return NBT_CODEC;
	}
	
}
