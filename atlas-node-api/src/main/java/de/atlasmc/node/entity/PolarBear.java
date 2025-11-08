package de.atlasmc.node.entity;

import de.atlasmc.nbt.codec.NBTCodec;

public interface PolarBear extends Animal, AngerableMob {
	
	public static final NBTCodec<PolarBear>
	NBT_HANDLER = NBTCodec
					.builder(PolarBear.class)
					.include(Animal.NBT_CODEC)
					.include(AngerableMob.NBT_CODEC)
					.boolField("StandingUp", PolarBear::isStandingUp, PolarBear::setStandingUp, false) // non standard
					.build();
	
	boolean isStandingUp();
	
	void setStandingUp(boolean standing);

	@Override
	default NBTCodec<? extends PolarBear> getNBTCodec() {
		return NBT_HANDLER;
	}
	
}
