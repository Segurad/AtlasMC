package de.atlasmc.node.world.particle;

import org.joml.Vector3d;

import de.atlasmc.Color;
import de.atlasmc.io.codec.StreamCodec;
import de.atlasmc.io.codec.StreamCodecs;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.nbt.codec.NBTCodecs;

public class TrailParticle extends AbstractParticle {

	public static final NBTCodec<TrailParticle>
	NBT_CODEC = NBTCodec
				.builder(TrailParticle.class)
				.include(AbstractParticle.NBT_CODEC)
				.codec("target", TrailParticle::getTarget, TrailParticle::setTarget, NBTCodecs.VECTOR_3D)
				.codec("color", TrailParticle::getColor, TrailParticle::setColor, Color.NBT_CODEC)
				.intField("duration", TrailParticle::getDuration, TrailParticle::setDuration)
				.build();
	
	public static final StreamCodec<TrailParticle>
	STREAM_CODEC = StreamCodec
					.builder(TrailParticle.class)
					.include(AbstractParticle.STREAM_CODEC)
					.codec(TrailParticle::getTarget, TrailParticle::setTarget, StreamCodecs.VECTOR_3D)
					.codec(TrailParticle::getColor, TrailParticle::setColor, Color.STREAM_CODEC)
					.varInt(TrailParticle::getDuration, TrailParticle::setDuration)
					.build();
	
	private Vector3d target;
	private Color color;
	private int duration;
	
	public TrailParticle(ParticleType type) {
		super(type);
	}
	
	public Color getColor() {
		return color;
	}
	
	public Vector3d getTarget() {
		return target;
	}
	
	public int getDuration() {
		return duration;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public void setTarget(Vector3d target) {
		this.target = target;
	}
	
	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	@Override
	public NBTCodec<? extends TrailParticle> getNBTCodec() {
		return NBT_CODEC;
	}
	
	@Override
	public StreamCodec<? extends TrailParticle> getStreamCodec() {
		return STREAM_CODEC;
	}

}
