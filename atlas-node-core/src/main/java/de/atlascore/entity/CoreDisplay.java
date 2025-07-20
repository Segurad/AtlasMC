package de.atlascore.entity;

import org.joml.Quaternionf;
import org.joml.Vector3f;

import de.atlasmc.Color;
import de.atlasmc.entity.Display;
import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.data.MetaData;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;

public class CoreDisplay extends CoreEntity implements Display {

	protected static final MetaDataField<Integer> META_INTERPOLATION_DELAY = new MetaDataField<>(CoreEntity.LAST_META_INDEX+1, 0, MetaDataType.VAR_INT);
	protected static final MetaDataField<Integer> META_TRANSFORMATION_INTERPOLATION_DURATION = new MetaDataField<>(CoreEntity.LAST_META_INDEX+2, 0, MetaDataType.VAR_INT);
	protected static final MetaDataField<Integer> META_POSITION_INTERPOLATION_DURATION = new MetaDataField<>(CoreEntity.LAST_META_INDEX+3, 0, MetaDataType.VAR_INT);
	protected static final MetaDataField<Vector3f> META_TRANSLATION = new MetaDataField<>(CoreEntity.LAST_META_INDEX+4, new Vector3f(), MetaDataType.VECTOR_3);
	protected static final MetaDataField<Vector3f> META_SCALE = new MetaDataField<>(CoreEntity.LAST_META_INDEX+5, new Vector3f(1f,1f,1f), MetaDataType.VECTOR_3);
	protected static final MetaDataField<Quaternionf> META_LEFT_ROTATION = new MetaDataField<>(CoreEntity.LAST_META_INDEX+6, new Quaternionf(), MetaDataType.QUATERNION);
	protected static final MetaDataField<Quaternionf> META_RIGHT_ROTATION = new MetaDataField<>(CoreEntity.LAST_META_INDEX+7, new Quaternionf(), MetaDataType.QUATERNION);
	protected static final MetaDataField<Byte> META_BILLBOARD = new MetaDataField<>(CoreEntity.LAST_META_INDEX+8, (byte) 0, MetaDataType.BYTE); 
	protected static final MetaDataField<Integer> META_BRIGHTNESS_OVERRIDE = new MetaDataField<>(CoreEntity.LAST_META_INDEX+9, -1, MetaDataType.VAR_INT);
	protected static final MetaDataField<Float> META_VIEW_RANGE = new MetaDataField<>(CoreEntity.LAST_META_INDEX+10, 1f, MetaDataType.FLOAT);
	protected static final MetaDataField<Float> META_SHADOW_RADIUS = new MetaDataField<>(CoreEntity.LAST_META_INDEX+11, 0f, MetaDataType.FLOAT);
	protected static final MetaDataField<Float> META_SHADOW_STRENGTH = new MetaDataField<>(CoreEntity.LAST_META_INDEX+12, 1f, MetaDataType.FLOAT);
	protected static final MetaDataField<Float> META_WIDTH = new MetaDataField<>(CoreEntity.LAST_META_INDEX+13, 0f, MetaDataType.FLOAT);
	protected static final MetaDataField<Float> META_HEIGHT = new MetaDataField<>(CoreEntity.LAST_META_INDEX+14, 0f, MetaDataType.FLOAT);
	protected static final MetaDataField<Integer> META_GLOW_COLOR_OVERRIDE = new MetaDataField<>(CoreEntity.LAST_META_INDEX+15, -1, MetaDataType.VAR_INT);
	
	protected static final int LAST_META_INDEX = CoreEntity.LAST_META_INDEX + 15;
	
	public CoreDisplay(EntityType type) {
		super(type);
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_INTERPOLATION_DELAY);
		metaContainer.set(META_TRANSFORMATION_INTERPOLATION_DURATION);
		metaContainer.set(META_TRANSLATION);
		metaContainer.set(META_SCALE);
		metaContainer.set(META_LEFT_ROTATION);
		metaContainer.set(META_RIGHT_ROTATION);
		metaContainer.set(META_BILLBOARD);
		metaContainer.set(META_BRIGHTNESS_OVERRIDE);
		metaContainer.set(META_VIEW_RANGE);
		metaContainer.set(META_SHADOW_RADIUS);
		metaContainer.set(META_SHADOW_STRENGTH);
		metaContainer.set(META_WIDTH);
		metaContainer.set(META_HEIGHT);
		metaContainer.set(META_GLOW_COLOR_OVERRIDE);
	}

	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX + 1;
	}
	
	@Override
	public Brightness getBrightness() {
		int val = metaContainer.getData(META_BRIGHTNESS_OVERRIDE);
		if (val == -1)
			return null;
		int skyLight = (val >> 20) & 0xF;
		int blockLight = (val >> 4) & 0xF;
		return new Brightness(blockLight, skyLight);
	}

	@Override
	public void setBrightness(Brightness brightness) {
		int val = -1;
		if (brightness != null)
			val = brightness.getBlockLightLevel() << 4 | brightness.getSkyLightLevel() << 20;
		metaContainer.get(META_BRIGHTNESS_OVERRIDE).setData(val);
	}

	@Override
	public boolean hasBrightness() {
		return metaContainer.getData(META_BRIGHTNESS_OVERRIDE) != -1;
	}

	@Override
	public Billboard getBillboard() { 
		return Billboard.getByID(metaContainer.getData(META_BILLBOARD));
	}

	@Override
	public void setBillboard(Billboard billboard) {
		if (billboard == null)
			throw new IllegalArgumentException("Billboard can not be null!");
		metaContainer.get(META_BILLBOARD).setData((byte) billboard.getID());
	}

	@Override
	public Color getGlowColorOverride() {
		int val = metaContainer.getData(META_GLOW_COLOR_OVERRIDE);
		if (val == -1)
			return null;
		return Color.fromARGB(val);
	}

	@Override
	public void setGlowColorOverride(Color color) {
		int val = -1;
		if (color != null)
			val = color.asARGB();
		metaContainer.get(META_GLOW_COLOR_OVERRIDE).setData(val);
	}

	@Override
	public float getDisplayWidth() {
		return metaContainer.getData(META_WIDTH).intValue();
	}

	@Override
	public void setDisplayWidth(float width) {
		metaContainer.get(META_WIDTH).setData(width);
	}

	@Override
	public float getDisplayHeight() {
		return metaContainer.getData(META_HEIGHT).intValue();
	}

	@Override
	public void setDisplayHeight(float height) {
		metaContainer.get(META_HEIGHT).setData(height);
	}

	@Override
	public int getTransformationInterpolationDuration() {
		return metaContainer.getData(META_TRANSFORMATION_INTERPOLATION_DURATION);
	}

	@Override
	public void setTransformationInterpolationDuration(int duration) {
		metaContainer.get(META_TRANSFORMATION_INTERPOLATION_DURATION).setData(duration);
	}

	@Override
	public int getInterpolationDelay() {
		return metaContainer.getData(META_INTERPOLATION_DELAY);
	}

	@Override
	public void setInterpolationDelay(int delay) {
		metaContainer.get(META_INTERPOLATION_DELAY).setData(delay);
	}

	@Override
	public float getShadowRadius() {
		return metaContainer.getData(META_SHADOW_RADIUS);
	}

	@Override
	public void setShadowRadius(float radius) {
		metaContainer.get(META_SHADOW_RADIUS).setData(radius);
	}

	@Override
	public float getShadowStrength() {
		return metaContainer.getData(META_SHADOW_STRENGTH);
	}

	@Override
	public void setShadowStrength(float strength) {
		metaContainer.get(META_SHADOW_STRENGTH).setData(strength);
	}

	@Override
	public float getViewRange() {
		return metaContainer.getData(META_VIEW_RANGE);
	}

	@Override
	public void setViewRange(float range) {
		metaContainer.get(META_VIEW_RANGE).setData(range);
	}

	@Override
	public Transformation getTransformation() {
		return getTransformation(new Transformation());
	}
	
	@Override
	public Transformation getTransformation(Transformation transformation) {
		if (transformation == null)
			throw new IllegalArgumentException("Transformation can not be null!");
		transformation.getScale().set(metaContainer.getData(META_SCALE));
		transformation.getTranslation().set(metaContainer.getData(META_TRANSLATION));
		transformation.getRotationLeft().set(metaContainer.getData(META_LEFT_ROTATION));
		transformation.getRotationRight().set(metaContainer.getData(META_RIGHT_ROTATION));
		return transformation;
	}

	@Override
	public void setTransformation(Transformation transformation) {
		if (transformation == null) {
			MetaData<Vector3f> scale = metaContainer.get(META_SCALE);
			if (!scale.getData().equals(META_SCALE.getDefaultData())) {
				scale.getData().set(META_SCALE.getDefaultData());
				scale.setChanged(true);
			}
			MetaData<Vector3f> translation = metaContainer.get(META_TRANSLATION);
			if (!translation.getData().equals(META_TRANSLATION.getDefaultData())) {
				translation.getData().set(META_TRANSLATION.getDefaultData());
				translation.setChanged(true);
			}
			MetaData<Quaternionf> rightRotation = metaContainer.get(META_RIGHT_ROTATION);
			if (!rightRotation.getData().equals(META_RIGHT_ROTATION.getDefaultData())) {
				rightRotation.getData().set(META_RIGHT_ROTATION.getDefaultData());
				rightRotation.setChanged(true);
			}
			MetaData<Quaternionf> leftRotation = metaContainer.get(META_LEFT_ROTATION);
			if (!leftRotation.getData().equals(META_LEFT_ROTATION.getDefaultData())) {
				leftRotation.getData().set(META_LEFT_ROTATION.getDefaultData());
				leftRotation.setChanged(true);
			}
		} else {
			MetaData<Vector3f> scale = metaContainer.get(META_SCALE);
			if (!scale.getData().equals(transformation.getScale())) {
				scale.getData().set(transformation.getScale());
				scale.setChanged(true);
			}
			MetaData<Vector3f> translation = metaContainer.get(META_TRANSLATION);
			if (!translation.getData().equals(transformation.getTranslation())) {
				translation.getData().set(transformation.getTranslation());
				translation.setChanged(true);
			}
			MetaData<Quaternionf> rightRotation = metaContainer.get(META_RIGHT_ROTATION);
			if (!rightRotation.getData().equals(transformation.getRotationRight())) {
				rightRotation.getData().set(transformation.getRotationRight());
				rightRotation.setChanged(true);
			}
			MetaData<Quaternionf> leftRotation = metaContainer.get(META_LEFT_ROTATION);
			if (!leftRotation.getData().equals(transformation.getRotationLeft())) {
				leftRotation.getData().set(transformation.getRotationLeft());
				leftRotation.setChanged(true);
			}
		}
	}

	@Override
	public int getPositionInterpolationDuration() {
		return metaContainer.getData(META_POSITION_INTERPOLATION_DURATION);
	}

	@Override
	public void setPositionInterpolationDuration(int duration) {
		metaContainer.get(META_POSITION_INTERPOLATION_DURATION).setData(duration);
	}

}
