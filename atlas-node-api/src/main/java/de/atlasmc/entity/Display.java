package de.atlasmc.entity;

import java.util.List;
import java.util.Objects;

import org.joml.Quaternionf;
import org.joml.Vector3f;

import de.atlasmc.Color;

public interface Display extends Entity {
	
	Brightness getBrightness();
	
	void setBrightness(Brightness brightness);
	
	boolean hasBrightness();
	
	Billboard getBillboard();
	
	void setBillboard(Billboard billboard);
	
	Color getGlowColorOverride();
	
	void setGlowColorOverride(Color color);
	
	int getDisplayWidth();
	
	void setDisplayWidth(int width);
	
	int getDisplayHeight();
	
	void setDisplayHeight(int height);
	
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
	
	public static final class Transformation {
		
		private final Vector3f scale;
		private final Vector3f translation;
		private final Quaternionf rotationRight;
		private final Quaternionf rotationLeft;
		
		public Transformation(Vector3f scale, Vector3f translation, Quaternionf rotationRight, Quaternionf rotationLeft)  {
			this.scale = scale;
			this.translation = translation;
			this.rotationLeft = rotationLeft;
			this.rotationRight = rotationRight;
		}
		
		public Vector3f getScale() {
			return scale;
		}
		
		public Vector3f getTranslation() {
			return translation;
		}
		
		public Quaternionf getRotationLeft() {
			return rotationLeft;
		}
		
		public Quaternionf getRotationRight() {
			return rotationRight;
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
		
	}
	
	public static final class Brightness {
		
		private final int blockLightLevel;
		private final int skyLightLevel;
		
		public Brightness(int blockLightLevel, int skyLightLevel) {
			if (blockLightLevel < 0 || blockLightLevel > 15) {
				throw new IllegalArgumentException("Block light level must be between 0 and 15: " + blockLightLevel);
			}
			if (skyLightLevel < 0 || skyLightLevel > 15) {
				throw new IllegalArgumentException("Sky light level must be between 0 and 15: " + skyLightLevel);
			}
			this.blockLightLevel = blockLightLevel;
			this.skyLightLevel = skyLightLevel;
		}
		
		public int getBlockLightLevel() {
			return blockLightLevel;
		}
		
		public int getSkyLightLevel() {
			return skyLightLevel;
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
		
	}
	
	public static enum Billboard {

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
		
		private String nameID;
		
		private Billboard() {
			nameID = name().toLowerCase();
		}
		
		public String getNameID() {
			return nameID;
		}
		
		public static Billboard getByNameID(String nameID) {
			if (nameID == null)
				throw new IllegalArgumentException("NameID can not be null!");
			for (Billboard value : getValues()) {
				if (value.getNameID().equals(nameID))
					return value;
			}
			throw new IllegalArgumentException("No value with name found: " + nameID);
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
