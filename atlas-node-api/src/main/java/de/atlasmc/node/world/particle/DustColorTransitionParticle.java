package de.atlasmc.node.world.particle;

import de.atlasmc.Color;
import de.atlasmc.io.codec.StreamCodec;
import de.atlasmc.nbt.codec.NBTCodec;

public class DustColorTransitionParticle extends AbstractParticle {
	
	public static final NBTCodec<DustColorTransitionParticle>
	NBT_CODEC = NBTCodec
				.builder(DustColorTransitionParticle.class)
				.include(AbstractParticle.NBT_CODEC)
				.codec("from_color", DustColorTransitionParticle::getFrom, DustColorTransitionParticle::setFrom, Color.NBT_CODEC)
				.codec("to_color", DustColorTransitionParticle::getTo, DustColorTransitionParticle::setTo, Color.NBT_CODEC)
				.floatField("scale", DustColorTransitionParticle::getScale, DustColorTransitionParticle::setScale)
				.build();
	
	public static final StreamCodec<DustColorTransitionParticle>
	STREAM_CODEC = StreamCodec
					.builder(DustColorTransitionParticle.class)
					.include(AbstractParticle.STREAM_CODEC)
					.codec(DustColorTransitionParticle::getFrom, DustColorTransitionParticle::setFrom, Color.STREAM_CODEC)
					.codec(DustColorTransitionParticle::getTo, DustColorTransitionParticle::setTo, Color.STREAM_CODEC)
					.floatValue(DustColorTransitionParticle::getScale, DustColorTransitionParticle::setScale)
					.build();
	
	private Color from;
	private Color to;
	private float scale;

	public DustColorTransitionParticle(ParticleType type) {
		super(type);
	}
	
	public Color getFrom() {
		return from;
	}
	
	public Color getTo() {
		return to;
	}
	
	public float getScale() {
		return scale;
	}
	
	public void setFrom(Color from) {
		this.from = from;
	}
	
	public void setTo(Color to) {
		this.to = to;
	}
	
	public void setScale(float scale) {
		this.scale = scale;
	}
	
	@Override
	public NBTCodec<? extends DustColorTransitionParticle> getNBTCodec() {
		return NBT_CODEC;
	}
	
	@Override
	public StreamCodec<? extends DustColorTransitionParticle> getStreamCodec() {
		return STREAM_CODEC;
	}
	
}
