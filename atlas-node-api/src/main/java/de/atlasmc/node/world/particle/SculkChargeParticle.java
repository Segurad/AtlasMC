package de.atlasmc.node.world.particle;

import de.atlasmc.io.codec.StreamCodec;
import de.atlasmc.nbt.codec.NBTCodec;

public class SculkChargeParticle extends AbstractParticle {

	public static final NBTCodec<SculkChargeParticle>
	NBT_CODEC = NBTCodec
				.builder(SculkChargeParticle.class)
				.include(AbstractParticle.NBT_CODEC)
				.floatField("roll", SculkChargeParticle::getRoll, SculkChargeParticle::setRoll)
				.build();
	
	public static final StreamCodec<SculkChargeParticle>
	STREAM_CODEC = StreamCodec
					.builder(SculkChargeParticle.class)
					.include(AbstractParticle.STREAM_CODEC)
					.floatValue(SculkChargeParticle::getRoll, SculkChargeParticle::setRoll)
					.build();
	
	private float roll;
	
	public SculkChargeParticle(ParticleType type) {
		super(type);
	}
	
	public float getRoll() {
		return roll;
	}
	
	public void setRoll(float roll) {
		this.roll = roll;
	}
	
	@Override
	public NBTCodec<? extends SculkChargeParticle> getNBTCodec() {
		return NBT_CODEC;
	}
	
	@Override
	public StreamCodec<? extends SculkChargeParticle> getStreamCodec() {
		return STREAM_CODEC;
	}

}
