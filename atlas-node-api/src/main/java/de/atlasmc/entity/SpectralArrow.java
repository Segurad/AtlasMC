package de.atlasmc.entity;

import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface SpectralArrow extends AbstractArrow {

	public static final NBTSerializationHandler<SpectralArrow>
	NBT_HANDLER = NBTSerializationHandler
					.builder(SpectralArrow.class)
					.include(AbstractArrow.NBT_HANDLER)
					.intField("Duration", SpectralArrow::getDuration, SpectralArrow::setDuration, 0)
					.build();
	
	void setDuration(int ticks);
	
	int getDuration();
	
	@Override
	default NBTSerializationHandler<? extends AbstractArrow> getNBTHandler() {
		return NBT_HANDLER;
	}

}
