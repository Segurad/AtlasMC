package de.atlasmc.node.entity;

import de.atlasmc.util.nbt.codec.NBTCodec;

public interface ZombifiedPiglin extends Zombie, AngerableMob  {
	
	public static final NBTCodec<ZombifiedPiglin>
	NBT_HANDLER = NBTCodec
					.builder(ZombifiedPiglin.class)
					.include(Zombie.NBT_HANDLER)
					.include(AngerableMob.NBT_HANDLER)
					.build();
	
	@Override
	default NBTCodec<? extends ZombifiedPiglin> getNBTCodec() {
		return NBT_HANDLER;
	}

}
