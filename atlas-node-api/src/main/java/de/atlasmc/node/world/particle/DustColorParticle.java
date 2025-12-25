package de.atlasmc.node.world.particle;

import de.atlasmc.Color;
import de.atlasmc.io.codec.StreamCodec;
import de.atlasmc.nbt.codec.NBTCodec;

public class DustColorParticle extends AbstractParticle {

	public static final NBTCodec<DustColorParticle>
	NBT_CODEC = NBTCodec
				.builder(DustColorParticle.class)
				.include(AbstractParticle.NBT_CODEC)
				.codec("color", DustColorParticle::getColor, DustColorParticle::setColor, Color.NBT_CODEC)
				.floatField("scale", DustColorParticle::getScale, DustColorParticle::setScale)
				.build();
	
	public static final StreamCodec<DustColorParticle>
	STREAM_CODEC = StreamCodec
					.builder(DustColorParticle.class)
					.include(AbstractParticle.STREAM_CODEC)
					.codec(DustColorParticle::getColor, DustColorParticle::setColor, Color.STREAM_CODEC)
					.floatValue(DustColorParticle::getScale, DustColorParticle::setScale)
					.build();
	
	private Color color;
	private float scale;
	
	public DustColorParticle(ParticleType type) {
		super(type);
	}
	
	public Color getColor() {
		return color;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public float getScale() {
		return scale;
	}
	
	public void setScale(float scale) {
		this.scale = scale;
	}
	
	@Override
	public StreamCodec<? extends DustColorParticle> getStreamCodec() {
		return STREAM_CODEC;
	}
	
	@Override
	public NBTCodec<? extends DustColorParticle> getNBTCodec() {
		return NBT_CODEC;
	}

}
