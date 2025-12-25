package de.atlasmc.node.world.particle;

import de.atlasmc.io.codec.StreamCodec;
import de.atlasmc.nbt.codec.NBTCodec;

public class DragonBreathParticle extends AbstractParticle {

	public static final NBTCodec<DragonBreathParticle>
	NBT_CODEC = NBTCodec
				.builder(DragonBreathParticle.class)
				.include(AbstractParticle.NBT_CODEC)
				.floatField("power", DragonBreathParticle::getPower, DragonBreathParticle::setPower, 1)
				.build();
	
	public static final StreamCodec<DragonBreathParticle>
	STREAM_CODEC = StreamCodec
					.builder(DragonBreathParticle.class)
					.include(AbstractParticle.STREAM_CODEC)
					.floatValue(DragonBreathParticle::getPower, DragonBreathParticle::setPower)
					.build();
	
	private float power;
	
	public DragonBreathParticle(ParticleType type) {
		super(type);
	}
	
	public float getPower() {
		return power;
	}
	
	public void setPower(float power) {
		this.power = power;
	}
	
	@Override
	public NBTCodec<? extends DragonBreathParticle> getNBTCodec() {
		return NBT_CODEC;
	}
	
	@Override
	public StreamCodec<? extends DragonBreathParticle> getStreamCodec() {
		return STREAM_CODEC;
	}

}
