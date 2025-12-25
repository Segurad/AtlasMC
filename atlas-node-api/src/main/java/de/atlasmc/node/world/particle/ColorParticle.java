package de.atlasmc.node.world.particle;

import de.atlasmc.Color;
import de.atlasmc.io.codec.StreamCodec;
import de.atlasmc.nbt.codec.NBTCodec;

public class ColorParticle extends AbstractParticle {

	public static final NBTCodec<ColorParticle>
	NBT_CODEC = NBTCodec
				.builder(ColorParticle.class)
				.include(AbstractParticle.NBT_CODEC)
				.codec("color", ColorParticle::getColor, ColorParticle::setColor, Color.NBT_CODEC)
				.build();
	
	public static final StreamCodec<ColorParticle>
	STREAM_CODEC = StreamCodec
					.builder(ColorParticle.class)
					.include(AbstractParticle.STREAM_CODEC)
					.codec(ColorParticle::getColor, ColorParticle::setColor, Color.STREAM_CODEC)
					.build();
	
	private Color color;
	
	public ColorParticle(ParticleType type) {
		super(type);
	}
	
	public Color getColor() {
		return color;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	@Override
	public NBTCodec<? extends ColorParticle> getNBTCodec() {
		return NBT_CODEC;
	}
	
	@Override
	public StreamCodec<? extends ColorParticle> getStreamCodec() {
		return STREAM_CODEC;
	}

}
