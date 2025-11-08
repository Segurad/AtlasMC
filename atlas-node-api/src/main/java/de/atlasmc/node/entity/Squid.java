package de.atlasmc.node.entity;

import de.atlasmc.nbt.codec.NBTCodec;

public interface Squid extends WaterAnimal, AgeableMob {

	public static final NBTCodec<Squid>
	NBT_HANDLER = NBTCodec
					.builder(Squid.class)
					.include(WaterAnimal.NBT_HANDLER)
					.include(AgeableMob.NBT_CODEC)
					.build();
	
}
