package de.atlasmc.node.world.particle;

import de.atlasmc.util.nbt.codec.NBTSerializable;
import de.atlasmc.util.nbt.codec.NBTCodec;

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
