package de.atlasmc.node.entity;

import de.atlasmc.nbt.codec.NBTCodec;

public interface SpectralArrow extends AbstractArrow {

	public static final NBTCodec<SpectralArrow>
	NBT_HANDLER = NBTCodec
					.builder(SpectralArrow.class)
					.include(AbstractArrow.NBT_HANDLER)
					.intField("Duration", SpectralArrow::getDuration, SpectralArrow::setDuration, 0)
					.build();
	
	void setDuration(int ticks);
	
	int getDuration();
	
	@Override
	default NBTCodec<? extends AbstractArrow> getNBTCodec() {
		return NBT_HANDLER;
	}

}
