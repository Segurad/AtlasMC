package de.atlasmc.node.world.particle;

import de.atlasmc.io.codec.StreamCodec;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.node.world.positionsource.PositionSource;

public class VibrationParticle extends AbstractParticle {

	public static final NBTCodec<VibrationParticle>
	NBT_CODEC = NBTCodec
				.builder(VibrationParticle.class)
				.include(AbstractParticle.NBT_CODEC)
				.codec("destination", VibrationParticle::getDestination, VibrationParticle::setDestination, PositionSource.NBT_CODEC)
				.intField("arrival_in_ticks", VibrationParticle::getTravelTicks, VibrationParticle::setTravelTicks)
				.build();
	
	public static final StreamCodec<VibrationParticle>
	STREAM_CODEC = StreamCodec
					.builder(VibrationParticle.class)
					.include(AbstractParticle.STREAM_CODEC)
					.codec(VibrationParticle::getDestination, VibrationParticle::setDestination, PositionSource.STREAM_CODEC)
					.intValue(VibrationParticle::getTravelTicks, VibrationParticle::setTravelTicks)
					.build();
	
	private PositionSource destination;
	private int travelTicks;
	
	public VibrationParticle(ParticleType type) {
		super(type);
	}
	
	public PositionSource getDestination() {
		return destination;
	}
	
	public void setDestination(PositionSource destination) {
		this.destination = destination;
	}
	
	public int getTravelTicks() {
		return travelTicks;
	}
	
	public void setTravelTicks(int travelTicks) {
		this.travelTicks = travelTicks;
	}
	
	@Override
	public NBTCodec<? extends VibrationParticle> getNBTCodec() {
		return NBT_CODEC;
	}
	
	@Override
	public StreamCodec<? extends VibrationParticle> getStreamCodec() {
		return STREAM_CODEC;
	}

}
