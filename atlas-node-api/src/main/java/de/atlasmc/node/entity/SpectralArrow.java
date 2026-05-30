package de.atlasmc.node.entity;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.util.annotation.NotNull;

public interface SpectralArrow extends AbstractArrow {

	@NotNull
	public static final NBTCodec<SpectralArrow>
	NBT_CODEC = NBTCodec
					.builder(SpectralArrow.class)
					.include(AbstractArrow.NBT_CODEC)
					.intField("Duration", SpectralArrow::getDuration, SpectralArrow::setDuration, 0)
					.build();
	
	void setDuration(int ticks);
	
	int getDuration();
	
	@Override
	default NBTCodec<? extends AbstractArrow> getNBTCodec() {
		return NBT_CODEC;
	}

}
