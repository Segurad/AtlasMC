package de.atlasmc.node;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import de.atlasmc.util.AtlasEnum;
import de.atlasmc.util.nbt.serialization.NBTSerializable;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public class FireworkExplosion implements NBTSerializable {
	
	public static final NBTSerializationHandler<FireworkExplosion>
	NBT_HANDLER = NBTSerializationHandler
					.builder(FireworkExplosion.class)
					.defaultConstructor(FireworkExplosion::new)
					.enumStringField("shape", FireworkExplosion::getShape, FireworkExplosion::setShape, Shape::getByName, Shape.SMALL_BALL)
					.intArray("colors", FireworkExplosion::getColors, FireworkExplosion::setColors)
					.intArray("fade_colors", FireworkExplosion::getFadeColors, FireworkExplosion::setFadeColors)
					.boolField("has_trail", FireworkExplosion::hasTrail, FireworkExplosion::setTrail, false)
					.boolField("has_twinkle", FireworkExplosion::hasTwinkel, FireworkExplosion::setTwinkel, false)
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
	public NBTSerializationHandler<? extends FireworkExplosion> getNBTHandler() {
		return NBT_HANDLER;
	}
	
	public static enum Shape implements AtlasEnum {
		SMALL_BALL,
		LARGE_BALL,
		STAR,
		CREEPER,
		BURST;

		private static List<Shape> VALUES;
		
		private final String name;
		
		private Shape() {
			name = name().toLowerCase().intern();
		}
		
		@Override
		public int getID() {
			return ordinal();
		}
		
		public static Shape getByID(int id) {
			return getValues().get(id);
		}
		
		@Override
		public String getName() {
			return name;
		}
		
		/**
		 * Returns a immutable List of all Types.<br>
		 * This method avoid allocation of a new array not like {@link #values()}.
		 * @return list
		 */
		public static List<Shape> getValues() {
			if (VALUES == null)
				VALUES = List.of(values());
			return VALUES;
		}
		
		
		/**
		 * Returns the value represented by the name or null if no matching value has been found
		 * @param name the name of the value
		 * @return value or null
		 */
		public static Shape getByName(String name) {
			if (name == null)
				throw new IllegalArgumentException("Name can not be null!");
			List<Shape> values = getValues();
			final int size = values.size();
			for (int i = 0; i < size; i++) {
				Shape value = values.get(i);
				if (value.name.equals(name)) 
					return value;
			}
			return null;
		}
		
		/**
		 * Releases the system resources used from the values cache
		 */
		public static void freeValues() {
			VALUES = null;
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
