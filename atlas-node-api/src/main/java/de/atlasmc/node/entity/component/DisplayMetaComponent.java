package de.atlasmc.node.entity.component;

import java.util.Objects;

import org.joml.Quaternionf;
import org.joml.Vector3f;

import de.atlasmc.Color;
import de.atlasmc.IDHolder;
import de.atlasmc.component.AbstractHolderBoundComponent;
import de.atlasmc.component.ComponentType;
import de.atlasmc.io.metadata.MetaDataContainer;
import de.atlasmc.io.metadata.MetaDataField;
import de.atlasmc.io.metadata.MetaDataFieldProvider;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.nbt.codec.NBTCodecs;
import de.atlasmc.nbt.codec.NBTSerializable;
import de.atlasmc.node.entity.Entity;
import de.atlasmc.node.entity.EntityMetaTypes;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.enums.EnumName;
import de.atlasmc.util.enums.EnumUtil;

public class DisplayMetaComponent extends AbstractHolderBoundComponent<Entity> implements MetaDataFieldProvider {

	public static final MetaDataField<Integer> 
	META_INTERPOLATION_DELAY = new MetaDataField<>(8, 0, EntityMetaTypes.VAR_INT);
	public static final MetaDataField<Integer> 
	META_TRANSFORMATION_INTERPOLATION_DURATION = new MetaDataField<>(9, 0, EntityMetaTypes.VAR_INT);
	public static final MetaDataField<Integer> 
	META_POSITION_INTERPOLATION_DURATION = new MetaDataField<>(10, 0, EntityMetaTypes.VAR_INT);
	public static final MetaDataField<Vector3f> 
	META_TRANSLATION = new MetaDataField<>(11, new Vector3f(), EntityMetaTypes.VECTOR_3F);
	public static final MetaDataField<Vector3f> 
	META_SCALE = new MetaDataField<>(12, new Vector3f(1f,1f,1f), EntityMetaTypes.VECTOR_3F);
	public static final MetaDataField<Quaternionf> 
	META_LEFT_ROTATION = new MetaDataField<>(13, new Quaternionf(), EntityMetaTypes.QUATERNION_F);
	public static final MetaDataField<Quaternionf> 
	META_RIGHT_ROTATION = new MetaDataField<>(14, new Quaternionf(), EntityMetaTypes.QUATERNION_F);
	public static final MetaDataField<Byte> 
	META_BILLBOARD = new MetaDataField<>(15, (byte) Billboard.FIXED.getID(), EntityMetaTypes.BYTE); 
	public static final MetaDataField<Integer> 
	META_BRIGHTNESS_OVERRIDE = new MetaDataField<>(16, -1, EntityMetaTypes.VAR_INT);
	public static final MetaDataField<Float> 
	META_VIEW_RANGE = new MetaDataField<>(17, 1f, EntityMetaTypes.FLOAT);
	public static final MetaDataField<Float> 
	META_SHADOW_RADIUS = new MetaDataField<>(18, 0f, EntityMetaTypes.FLOAT);
	public static final MetaDataField<Float> 
	META_SHADOW_STRENGTH = new MetaDataField<>(19, 1f, EntityMetaTypes.FLOAT);
	public static final MetaDataField<Float> 
	META_WIDTH = new MetaDataField<>(20, 0f, EntityMetaTypes.FLOAT);
	public static final MetaDataField<Float> 
	META_HEIGHT = new MetaDataField<>(21, 0f, EntityMetaTypes.FLOAT);
	public static final MetaDataField<Integer> 
	META_GLOW_COLOR_OVERRIDE = new MetaDataField<>(22, -1, EntityMetaTypes.VAR_INT);
	
	@NotNull
	public static final NBTCodec<DisplayMetaComponent>
	NBT_CODEC = NBTCodec
					.builder(DisplayMetaComponent.class)
					.include(AbstractHolderBoundComponent.NBT_CODEC)
					.codec("billboard", DisplayMetaComponent::getBillboard, DisplayMetaComponent::setBillboard, EnumUtil.enumStringNBTCodec(Billboard.class), Billboard.FIXED)
					.codec("brightness", DisplayMetaComponent::getBrightness, DisplayMetaComponent::setBrightness, Brightness.NBT_CODEC)
					.codec("glow_color_override", DisplayMetaComponent::getGlowColorOverride, DisplayMetaComponent::setGlowColorOverride, Color.NBT_CODEC)
					.floatField("height", DisplayMetaComponent::getDisplayHeight, DisplayMetaComponent::setDisplayHeight, 0)
					.floatField("width", DisplayMetaComponent::getDisplayWidth, DisplayMetaComponent::setDisplayWidth, 0)
					.intField("interpolation_duration", DisplayMetaComponent::getTransformationInterpolationDuration, DisplayMetaComponent::setTransformationInterpolationDuration, 0)
					.intField("teleport_duration", DisplayMetaComponent::getPositionInterpolationDuration, DisplayMetaComponent::setPositionInterpolationDuration, 0)
					.intField("start_interpolation", DisplayMetaComponent::getInterpolationDelay, DisplayMetaComponent::setInterpolationDelay, 0)
					.floatField("shadow_radius", DisplayMetaComponent::getShadowRadius, DisplayMetaComponent::setShadowRadius, 0)
					.floatField("shadow_strength", DisplayMetaComponent::getShadowStrength, DisplayMetaComponent::setShadowStrength, 0)
					.floatField("view_range", DisplayMetaComponent::getViewRange, DisplayMetaComponent::setViewRange, 1)
					.codec("transformation", DisplayMetaComponent::getTransformation, DisplayMetaComponent::setTransformation, Transformation.NBT_CODEC)
					.build();
	
	public DisplayMetaComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public void initMetaContainer(MetaDataContainer container) {
		container.set(META_INTERPOLATION_DELAY);
		container.set(META_TRANSFORMATION_INTERPOLATION_DURATION);
		container.set(META_TRANSLATION);
		container.set(META_SCALE);
		container.set(META_LEFT_ROTATION);
		container.set(META_RIGHT_ROTATION);
		container.set(META_BILLBOARD);
		container.set(META_BRIGHTNESS_OVERRIDE);
		container.set(META_VIEW_RANGE);
		container.set(META_SHADOW_RADIUS);
		container.set(META_SHADOW_STRENGTH);
		container.set(META_WIDTH);
		container.set(META_HEIGHT);
		container.set(META_GLOW_COLOR_OVERRIDE);
	}

	@Override
	public int getMetaFieldCount() {
		return 14;
	}
	
	public Brightness getBrightness() {
		int val = getHolder().getMetaContainer().getData(META_BRIGHTNESS_OVERRIDE);
		if (val == -1)
			return null;
		int skyLight = (val >> 20) & 0xF;
		int blockLight = (val >> 4) & 0xF;
		return new Brightness(blockLight, skyLight);
	}

	public void setBrightness(Brightness brightness) {
		int val = -1;
		if (brightness != null)
			val = brightness.getBlockLightLevel() << 4 | brightness.getSkyLightLevel() << 20;
		getHolder().getMetaContainer().setData(META_BRIGHTNESS_OVERRIDE, val);
	}

	public boolean hasBrightness() {
		return getHolder().getMetaContainer().getData(META_BRIGHTNESS_OVERRIDE) != -1;
	}

	public Billboard getBillboard() { 
		return EnumUtil.getByID(Billboard.class, getHolder().getMetaContainer().getData(META_BILLBOARD));
	}

	public void setBillboard(Billboard billboard) {
		getHolder().getMetaContainer().setData(META_BILLBOARD, (byte) billboard.getID());
	}

	public Color getGlowColorOverride() {
		int val = getHolder().getMetaContainer().getData(META_GLOW_COLOR_OVERRIDE);
		if (val == -1)
			return null;
		return Color.fromARGB(val);
	}

	public void setGlowColorOverride(Color color) {
		int val = -1;
		if (color != null)
			val = color.asARGB();
		getHolder().getMetaContainer().setData(META_GLOW_COLOR_OVERRIDE, val);
	}

	public float getDisplayWidth() {
		return getHolder().getMetaContainer().getData(META_WIDTH).intValue();
	}

	public void setDisplayWidth(float width) {
		getHolder().getMetaContainer().setData(META_WIDTH, width);
	}

	public float getDisplayHeight() {
		return getHolder().getMetaContainer().getData(META_HEIGHT).intValue();
	}

	public void setDisplayHeight(float height) {
		getHolder().getMetaContainer().setData(META_HEIGHT, height);
	}

	public int getTransformationInterpolationDuration() {
		return getHolder().getMetaContainer().getData(META_TRANSFORMATION_INTERPOLATION_DURATION);
	}

	public void setTransformationInterpolationDuration(int duration) {
		getHolder().getMetaContainer().setData(META_TRANSFORMATION_INTERPOLATION_DURATION, duration);
	}

	public int getInterpolationDelay() {
		return getHolder().getMetaContainer().getData(META_INTERPOLATION_DELAY);
	}

	public void setInterpolationDelay(int delay) {
		getHolder().getMetaContainer().setData(META_INTERPOLATION_DELAY, delay);
	}

	public float getShadowRadius() {
		return getHolder().getMetaContainer().getData(META_SHADOW_RADIUS);
	}

	public void setShadowRadius(float radius) {
		getHolder().getMetaContainer().setData(META_SHADOW_RADIUS, radius);
	}

	public float getShadowStrength() {
		return getHolder().getMetaContainer().getData(META_SHADOW_STRENGTH);
	}

	public void setShadowStrength(float strength) {
		getHolder().getMetaContainer().setData(META_SHADOW_STRENGTH, strength);
	}

	public float getViewRange() {
		return getHolder().getMetaContainer().getData(META_VIEW_RANGE);
	}

	public void setViewRange(float range) {
		getHolder().getMetaContainer().setData(META_VIEW_RANGE, range);
	}

	public Transformation getTransformation() {
		return getTransformation(new Transformation());
	}
	
	public Transformation getTransformation(Transformation transformation) {
		var container = this.getHolder().getMetaContainer();
		transformation.getScale().set(container.getData(META_SCALE));
		transformation.getTranslation().set(container.getData(META_TRANSLATION));
		transformation.getRotationLeft().set(container.getData(META_LEFT_ROTATION));
		transformation.getRotationRight().set(container.getData(META_RIGHT_ROTATION));
		return transformation;
	}

	public void setTransformation(Transformation transformation) {
		var container = this.getHolder().getMetaContainer();
		if (transformation == null) {
			container.resetData(META_SCALE);
			container.resetData(META_TRANSLATION);
			container.resetData(META_RIGHT_ROTATION);
			container.resetData(META_LEFT_ROTATION);
		} else {
			container.setData(META_SCALE, transformation.getScale());
			container.setData(META_TRANSLATION, transformation.getTranslation());
			container.setData(META_RIGHT_ROTATION, transformation.getRotationRight());
			container.setData(META_LEFT_ROTATION, transformation.getRotationLeft());
		}
	}

	public int getPositionInterpolationDuration() {
		return getHolder().getMetaContainer().getData(META_POSITION_INTERPOLATION_DURATION);
	}

	public void setPositionInterpolationDuration(int duration) {
		getHolder().getMetaContainer().setData(META_POSITION_INTERPOLATION_DURATION, duration);
	}
	
	@Override
	public NBTCodec<? extends DisplayMetaComponent> getNBTCodec() {
		return NBT_CODEC;
	}
	
	public static final class Transformation implements NBTSerializable, Cloneable  {
		
		@NotNull
		public static final NBTCodec<Transformation>
		NBT_CODEC = NBTCodec
						.builder(Transformation.class)
						.codec("right_rotation", Transformation::getRotationRight, Transformation::setRotationRight, NBTCodecs.QUATERNION_F)
						.codec("scale", Transformation::getScale, Transformation::setScale, NBTCodecs.VECTOR_3F)
						.codec("left_rotation", Transformation::getRotationLeft, Transformation::setRotationLeft, NBTCodecs.QUATERNION_F)
						.codec("translation", Transformation::getTranslation, Transformation::setTranslation, NBTCodecs.VECTOR_3F)
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
		public NBTCodec<? extends Transformation> getNBTCodec() {
			return NBT_CODEC;
		}
		
	}
	
	public static final class Brightness implements NBTSerializable {
		
		@NotNull
		public static final NBTCodec<Brightness>
		NBT_CODEC = NBTCodec
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
		public NBTCodec<? extends Brightness> getNBTCodec() {
			return NBT_CODEC;
		}
		
	}
	
	public static enum Billboard implements EnumName, IDHolder {

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

		private final String name;
		
		private Billboard() {
			name = name().toLowerCase();
		}
		
		@Override
		public String getName() {
			return name;
		}
		
		@Override
		public int getID() {
			return ordinal();
		}

	}
	
}
