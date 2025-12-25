package de.atlasmc.node.world.particle;

import de.atlasmc.Color;
import de.atlasmc.io.codec.StreamCodec;
import de.atlasmc.nbt.codec.NBTCodec;

public class EffectParticle extends AbstractParticle {

	public static final NBTCodec<EffectParticle>
	NBT_CODEC = NBTCodec
				.builder(EffectParticle.class)
				.include(AbstractParticle.NBT_CODEC)
				.floatField("power", EffectParticle::getPower, EffectParticle::setPower, 1)
				.codec("color", EffectParticle::getColor, EffectParticle::setColor, Color.NBT_CODEC)
				.build();
	
	public static final StreamCodec<EffectParticle>
	STREAM_CODEC = StreamCodec
					.builder(EffectParticle.class)
					.include(AbstractParticle.STREAM_CODEC)
					.codec(EffectParticle::getColor, EffectParticle::setColor, Color.STREAM_CODEC)
					.floatValue(EffectParticle::getPower, EffectParticle::setPower)
					.build();
	
	private Color color;
	private float power;
	
	public EffectParticle(ParticleType type) {
		super(type);
	}
	
	public Color getColor() {
		return color;
	}
	
	public float getPower() {
		return power;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public void setPower(float power) {
		this.power = power;
	}
	
	@Override
	public NBTCodec<? extends EffectParticle> getNBTCodec() {
		return NBT_CODEC;
	}
	
	@Override
	public StreamCodec<? extends EffectParticle> getStreamCodec() {
		return STREAM_CODEC;
	}

}
