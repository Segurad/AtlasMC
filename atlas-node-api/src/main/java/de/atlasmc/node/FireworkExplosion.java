package de.atlasmc.node;

import java.util.Arrays;
import java.util.Objects;

import de.atlasmc.IDHolder;
import de.atlasmc.io.codec.StreamCodec;
import de.atlasmc.io.codec.StreamCodecs;
import de.atlasmc.io.codec.StreamSerializable;
import de.atlasmc.util.EnumName;
import de.atlasmc.util.nbt.codec.NBTSerializable;
import de.atlasmc.util.nbt.codec.NBTCodec;

public class FireworkExplosion implements NBTSerializable, StreamSerializable {
	
	public static final NBTCodec<FireworkExplosion>
	NBT_HANDLER = NBTCodec
					.builder(FireworkExplosion.class)
					.defaultConstructor(FireworkExplosion::new)
					.enumStringField("shape", FireworkExplosion::getShape, FireworkExplosion::setShape, Shape.class, Shape.SMALL_BALL)
					.intArray("colors", FireworkExplosion::getColors, FireworkExplosion::setColors)
					.intArray("fade_colors", FireworkExplosion::getFadeColors, FireworkExplosion::setFadeColors)
					.boolField("has_trail", FireworkExplosion::hasTrail, FireworkExplosion::setTrail, false)
					.boolField("has_twinkle", FireworkExplosion::hasTwinkel, FireworkExplosion::setTwinkel, false)
					.build();
	
	public static final StreamCodec<FireworkExplosion>
	STREAM_CODEC = StreamCodec
					.builder(FireworkExplosion.class)
					.defaultConstructor(FireworkExplosion::new)
					.varIntEnum(FireworkExplosion::getShape, FireworkExplosion::setShape, Shape.class)
					.codec(FireworkExplosion::getColors, FireworkExplosion::setColors, StreamCodecs.INT_ARRAY)
					.codec(FireworkExplosion::getFadeColors, FireworkExplosion::setFadeColors, StreamCodecs.INT_ARRAY)
					.booleanValue(FireworkExplosion::hasTrail, FireworkExplosion::setTrail)
					.booleanValue(FireworkExplosion::hasTwinkel, FireworkExplosion::setTwinkel)
					.build();
	
	private int[] colors;
	private int[] fadeColors;
	private boolean twinkel;
	private boolean trail;
	private Shape shape;
	
	public FireworkExplosion() {
		shape = Shape.SMALL_BALL;
	}
	
	public Shape getShape() {
		return shape;
	}
	
	public void setShape(Shape shape) {
		if (shape == null) 
			throw new IllegalArgumentException("Shape can not be null!");
		this.shape = shape;
	}
	
	public boolean hasTwinkel() {
		return twinkel;
	}
	
	public void setTwinkel(boolean twinkel) {
		this.twinkel = twinkel;
	}
	
	public boolean hasTrail() {
		return trail;
	}
	
	public void setTrail(boolean trail) {
		this.trail = trail;
	}
	
	public int[] getColors() {
		return colors;
	}
	
	public void setColors(int[] colors) {
		this.colors = colors;
	}
	
	public int[] getFadeColors() {
		return fadeColors;
	}
	
	public void setFadeColors(int[] fadeColors) {
		this.fadeColors = fadeColors;
	}
	
	public FireworkExplosion clone() {
		FireworkExplosion clone = null;
		try {
			clone = (FireworkExplosion) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		if (clone == null)
			return null;
		if (colors != null)
			clone.colors = colors.clone();
		if (fadeColors != null)
			clone.fadeColors = colors.clone();
		return clone;
	}
	
	@Override
	public NBTCodec<? extends FireworkExplosion> getNBTCodec() {
		return NBT_HANDLER;
	}
	
	@Override
	public StreamCodec<? extends FireworkExplosion> getStreamCodec() {
		return STREAM_CODEC;
	}
	
	public static enum Shape implements IDHolder, EnumName {
		
		SMALL_BALL,
		LARGE_BALL,
		STAR,
		CREEPER,
		BURST;

		private final String name;
		
		private Shape() {
			name = name().toLowerCase().intern();
		}
		
		@Override
		public int getID() {
			return ordinal();
		}
		
		@Override
		public String getName() {
			return name;
		}
		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(colors);
		result = prime * result + Arrays.hashCode(fadeColors);
		result = prime * result + Objects.hash(shape, trail, twinkel);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FireworkExplosion other = (FireworkExplosion) obj;
		return Arrays.equals(colors, other.colors) && Arrays.equals(fadeColors, other.fadeColors)
				&& shape == other.shape && trail == other.trail && twinkel == other.twinkel;
	}

}
