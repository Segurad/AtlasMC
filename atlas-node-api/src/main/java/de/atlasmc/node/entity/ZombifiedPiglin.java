package de.atlasmc.node.entity;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.util.annotation.NotNull;

public interface ZombifiedPiglin extends Zombie, AngerableMob  {
	
	@NotNull
	public static final NBTCodec<ZombifiedPiglin>
	NBT_CODEC = NBTCodec
					.builder(ZombifiedPiglin.class)
					.include(Zombie.NBT_CODEC)
					.include(AngerableMob.NBT_CODEC)
					.build();
	
	@Override
	default NBTCodec<? extends ZombifiedPiglin> getNBTCodec() {
		return NBT_CODEC;
	}

}
