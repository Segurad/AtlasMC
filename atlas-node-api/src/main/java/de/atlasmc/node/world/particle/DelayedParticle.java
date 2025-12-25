package de.atlasmc.node.world.particle;

import de.atlasmc.io.codec.StreamCodec;
import de.atlasmc.nbt.codec.NBTCodec;

public class DelayedParticle extends AbstractParticle {

	public static final NBTCodec<DelayedParticle>
	NBT_CODEC = NBTCodec
				.builder(DelayedParticle.class)
				.include(AbstractParticle.NBT_CODEC)
				.intField("delay", DelayedParticle::getDelay, DelayedParticle::setDelay)
				.build();
	
	public static final StreamCodec<DelayedParticle>
	STREAM_CODEC = StreamCodec
					.builder(DelayedParticle.class)
					.include(AbstractParticle.STREAM_CODEC)
					.varInt(DelayedParticle::getDelay, DelayedParticle::setDelay)
					.build();
	
	private int delay;
	
	public DelayedParticle(ParticleType type) {
		super(type);
	}
	
	public int getDelay() {
		return delay;
	}
	
	public void setDelay(int delay) {
		this.delay = delay;
	}
	
	@Override
	public NBTCodec<? extends DelayedParticle> getNBTCodec() {
		return NBT_CODEC;
	}
	
	@Override
	public StreamCodec<? extends DelayedParticle> getStreamCodec() {
		return STREAM_CODEC;
	}

}
