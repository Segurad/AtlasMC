package de.atlasmc.node.entity;

import java.util.List;
import java.util.Objects;

import org.joml.Quaternionf;
import org.joml.Vector3f;

import de.atlasmc.Color;
import de.atlasmc.util.EnumName;
import de.atlasmc.util.EnumValueCache;
import de.atlasmc.util.nbt.serialization.NBTSerializable;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface Display extends Entity {
	
	public static final NBTSerializationHandler<Display>
	NBT_HANDLER = NBTSerializationHandler
					.builder(Display.class)
					.include(Entity.NBT_HANDLER)
					.enumStringField("billboard", Display::getBillboard, Display::setBillboard, Billboard::getByName, Billboard.FIXED)
					.typeCompoundField("brightness", Display::getBrightness, Display::setBrightness, Brightness.NBT_HANDLER)
					.color("glow_color_override", Display::getGlowColorOverride, Display::setGlowColorOverride)
					.floatField("height", Display::getDisplayHeight, Display::setDisplayHeight, 0)
					.floatField("width", Display::getDisplayWidth, Display::setDisplayWidth, 0)
					.intField("interpolation_duration", Display::getTransformationInterpolationDuration, Display::setTransformationInterpolationDuration, 0)
					.intField("teleport_duration", Display::getPositionInterpolationDuration, Display::setPositionInterpolationDuration, 0)
					.intField("start_interpolation", Display::getInterpolationDelay, Display::setInterpolationDelay, 0)
					.floatField("shadow_radius", Display::getShadowRadius, Display::setShadowRadius, 0)
					.floatField("shadow_strength", Display::getShadowStrength, Display::setShadowStrength, 0)
					.floatField("view_range", Display::getViewRange, Display::setViewRange, 1)
					.typeCompoundField("transformation", Display::getTransformation, Display::setTransformation, Transformation.NBT_HANDLER)
					.build();
	
	Brightness getBrightness();
	
	void setBrightness(Brightness brightness);
	
	boolean hasBrightness();
	
	Billboard getBillboard();
	
	void setBillboard(Billboard billboard);
	
	Color getGlowColorOverride();
	
	void setGlowColorOverride(Color color);
	
	float getDisplayWidth();
	
	void setDisplayWidth(float width);
	
	float getDisplayHeight();
	
	void setDisplayHeight(float height);
	
	int getTransformationInterpolationDuration();
	
	void setTransformationInterpolationDuration(int duration);
	
	int getPositionInterpolationDuration();
	
	void setPositionInterpolationDuration(int duration);
	
	int getInterpolationDelay();
	
	void setInterpolationDelay(int delay);
	
	float getShadowRadius();
	
	void setShadowRadius(float radius);
	
	float getShadowStrength();
	
	void setShadowStrength(float strength);
	
	float getViewRange();
	
	void setViewRange(float range);
	
	Transformation getTransformation();
	
	Transformation getTransformation(Transformation transformation);
	
	void setTransformation(Transformation transformation);
	
	@Override
	default NBTSerializationHandler<? extends Display> getNBTHandler() {
		return NBT_HANDLER;
	}
	
	public static final class Transformation implements NBTSerializable, Cloneable  {
		
		public static final NBTSerializationHandler<Transformation>
		NBT_HANDLER = NBTSerializationHandler
						.builder(Transformation.class)
						.quaternionf("right_rotation", Transformation::getRotationRight, Transformation::setRotationRight)
						.vector3f("scale", Transformation::getScale, Transformation::setScale)
						.quaternionf("left_rotation", Transformation::getRotationLeft, Transformation::setRotationLeft)
						.vector3f("translation", Transformation::getTranslation, Transformation::setTranslation)
						.build();
		
		private final Vector3f scale;
		private final Vector3f translation;
		private final Quaternionf rotationRight;
		private final Quaternionf rotationLeft;
		
		public Transformation() {
			this(new Vector3f(), new Vector3f(), new Quaternionf(), new Quaternionf());
		}
		
		public Transformation(Vector3f scale, Vector3f translation, Quaternionf rotationRight, Quaternionf rotationLeft)  {
			this.scale = scale;
			this.translation = translation;
			this.rotationLeft = rotationLeft;
			this.rotationRight = rotationRight;
		}
		
		public Vector3f getScale() {
			return scale;
		}
		
		public Transformation setScale(Vector3f scale) {
			this.scale.set(scale);
			return this;
		}
		
		public Vector3f getTranslation() {
			return translation;
		}
		
		public Transformation setTranslation(Vector3f translation) {
			this.translation.set(translation);
			return this;
		}
		
		public Quaternionf getRotationLeft() {
			return rotationLeft;
		}
		
		public Transformation setRotationLeft(Quaternionf rotation) {
			this.rotationLeft.set(rotation);
			return this;
		}
		
		public Quaternionf getRotationRight() {
			return rotationRight;
		}
		
		public Transformation setRotationRight(Quaternionf rotation) {
			this.rotationRight.set(rotation);
			return this;
		}

		@Override
		public int hashCode() {
			return Objects.hash(rotationLeft, rotationRight, scale, translation);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Transformation other = (Transformation) obj;
			return Objects.equals(rotationLeft, other.rotationLeft)
					&& Objects.equals(rotationRight, other.rotationRight) && Objects.equals(scale, other.scale)
					&& Objects.equals(translation, other.translation);
		}
		
		@Override
		public Transformation clone() {
			return new Transformation(new Vector3f(scale), new Vector3f(translation), new Quaternionf(rotationRight), new Quaternionf(rotationLeft));
		}
		
		@Override
		public NBTSerializationHandler<? extends Transformation> getNBTHandler() {
			return NBT_HANDLER;
		}
		
	}
	
	public static final class Brightness implements NBTSerializable {
		
		public static final NBTSerializationHandler<Brightness>
		NBT_HANDLER = NBTSerializationHandler
						.builder(Brightness.class)
						.defaultConstructor(Brightness::new)
						.intField("block", Brightness::getBlockLightLevel, Brightness::setBlockLightLevel)
						.intField("sky", Brightness::getSkyLightLevel, Brightness::setSkyLightLevel)
						.build();
		
		private int blockLightLevel;
		private int skyLightLevel;
		
		public Brightness(int blockLightLevel, int skyLightLevel) {
			setBlockLightLevel(blockLightLevel);
			setSkyLightLevel(skyLightLevel);
		}
		
		private Brightness() {}
		
		public int getBlockLightLevel() {
			return blockLightLevel;
		}
		
		private void setBlockLightLevel(int blockLightLevel) {
			if (blockLightLevel < 0 || blockLightLevel > 15) {
				throw new IllegalArgumentException("Block light level must be between 0 and 15: " + blockLightLevel);
			}
			this.blockLightLevel = blockLightLevel;
		}
		
		public int getSkyLightLevel() {
			return skyLightLevel;
		}
		
		private void setSkyLightLevel(int skyLightLevel) {
			if (skyLightLevel < 0 || skyLightLevel > 15) {
				throw new IllegalArgumentException("Sky light level must be between 0 and 15: " + skyLightLevel);
			}
			this.skyLightLevel = skyLightLevel;
		}

		@Override
		public int hashCode() {
			return Objects.hash(blockLightLevel, skyLightLevel);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Brightness other = (Brightness) obj;
			return blockLightLevel == other.blockLightLevel && skyLightLevel == other.skyLightLevel;
		}
		
		@Override
		public NBTSerializationHandler<? extends Brightness> getNBTHandler() {
			return NBT_HANDLER;
		}
		
	}
	
	public static enum Billboard implements EnumName, EnumValueCache {

		/**
		 * Vertical and horizontal axis are fixed
		 */
		FIXED,
		/**
		 * faces player around vertical axis
		 */
		VERTIAL,
		/**
		 * pivots around horizontal axis
		 */
		HORIZONTAL,
		/**
		 * pivots around center point
		 */
		CENTER;
		
		private static List<Billboard> VALUES;
		
		private String name;
		
		private Billboard() {
			name = name().toLowerCase();
		}
		
		@Override
		public String getName() {
			return name;
		}
		
		public static Billboard getByName(String name) {
			if (name == null)
				throw new IllegalArgumentException("NameID can not be null!");
			final List<Billboard> values = getValues();
			final int size = values.size();
			for (int i = 0; i < size; i++) {
				Billboard value = values.get(i);
				if (value.name.equals(name))
					return value;
			}
			throw new IllegalArgumentException("No value with name found: " + name);
		}
		
		public int getID() {
			return ordinal();
		}
		
		public static Billboard getByID(int id) {
			return getValues().get(id);
		}
		
		/**
		 * Returns a immutable List of all Types.<br>
		 * This method avoid allocation of a new array not like {@link #values()}.
		 * @return list
		 */
		public static List<Billboard> getValues() {
			if (VALUES == null)
				VALUES = List.of(values());
			return VALUES;
		}

		/**
		 * Releases the system resources used from the values cache
		 */
		public static void freeValues() {
			VALUES = null;
		}

	}

}
