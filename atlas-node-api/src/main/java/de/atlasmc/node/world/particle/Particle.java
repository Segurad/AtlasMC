package de.atlasmc.node.world.particle;

import de.atlasmc.io.codec.StreamCodec;
import de.atlasmc.io.codec.StreamSerializable;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.nbt.codec.NBTSerializable;
import de.atlasmc.util.OpenCloneable;

public interface Particle extends NBTSerializable, StreamSerializable , OpenCloneable {
	
	public static final NBTCodec<Particle>
	NBT_CODEC = NBTCodec
					.builder(Particle.class)
					.searchKeyEnumConstructor("type", ParticleType.class, ParticleType::createParticle, Particle::getType)
					.build();
	
	public static final StreamCodec<Particle>
	STREAM_CODEC = StreamCodec
					.builder(Particle.class)
					.constr
					.build();
	
	ParticleType getType();
	
	Particle clone();
	
	@Override
	default NBTCodec<? extends Particle> getNBTCodec() {
		return NBT_CODEC;
	}

}
