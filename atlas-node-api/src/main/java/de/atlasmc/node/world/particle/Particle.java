package de.atlasmc.node.world.particle;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.nbt.codec.NBTSerializable;

public interface Particle extends NBTSerializable, Cloneable {
	
	public static final NBTCodec<Particle>
	NBT_HANDLER = NBTCodec
					.builder(Particle.class)
					.build();
	
	Particle clone();
	
	@Override
	default NBTCodec<? extends Particle> getNBTCodec() {
		return NBT_HANDLER;
	}

}
