package de.atlasmc.world.particle;

import de.atlasmc.util.nbt.serialization.NBTSerializable;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface Particle extends NBTSerializable, Cloneable {
	
	public static final NBTSerializationHandler<Particle>
	NBT_HANDLER = NBTSerializationHandler
					.builder(Particle.class)
					.build();
	
	Particle clone();
	
	@Override
	default NBTSerializationHandler<? extends Particle> getNBTHandler() {
		return NBT_HANDLER;
	}

}
