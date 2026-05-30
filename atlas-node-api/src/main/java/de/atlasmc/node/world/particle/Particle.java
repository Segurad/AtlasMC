package de.atlasmc.node.world.particle;

import de.atlasmc.io.codec.StreamCodec;
import de.atlasmc.io.codec.StreamSerializable;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.nbt.codec.NBTSerializable;
import de.atlasmc.util.OpenCloneable;
import de.atlasmc.util.annotation.NotNull;

public interface Particle extends NBTSerializable, StreamSerializable , OpenCloneable {
	
	@NotNull
	public static final NBTCodec<Particle>
	NBT_CODEC = NBTCodec
					.builder(Particle.class)
					.searchKeyEnumConstructor("type", ParticleType.class, ParticleType::createParticle, Particle::getType)
					.build();
	
	@NotNull
	public static final StreamCodec<Particle>
	STREAM_CODEC = StreamCodec
					.builder(Particle.class)
					.enumVarIntConstructor(ParticleType.class, ParticleType::createParticle, Particle::getType)
					.build();
	
	@NotNull
	ParticleType getType();
	
	@Override
	Particle clone();
	
	@Override
	default NBTCodec<? extends Particle> getNBTCodec() {
		return NBT_CODEC;
	}
	
	@Override
	default StreamCodec<? extends Particle> getStreamCodec() {
		return STREAM_CODEC;
	}

}
