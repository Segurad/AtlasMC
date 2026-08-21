package de.atlasmc.core.node.entity;

import org.joml.Quaternionf;
import org.joml.Vector3f;

import de.atlasmc.Color;
import de.atlasmc.io.metadata.MetaDataField;
import de.atlasmc.node.entity.Display;
import de.atlasmc.node.entity.EntityMetaTypes;
import de.atlasmc.node.entity.EntityType;
import de.atlasmc.util.enums.EnumUtil;

public class CoreDisplay extends CoreEntity implements Display {

	protected static final MetaDataField<Integer> META_INTERPOLATION_DELAY = new MetaDataField<>(CoreEntity.LAST_META_INDEX+1, 0, EntityMetaTypes.VAR_INT);
	protected static final MetaDataField<Integer> META_TRANSFORMATION_INTERPOLATION_DURATION = new MetaDataField<>(CoreEntity.LAST_META_INDEX+2, 0, EntityMetaTypes.VAR_INT);
	protected static final MetaDataField<Integer> META_POSITION_INTERPOLATION_DURATION = new MetaDataField<>(CoreEntity.LAST_META_INDEX+3, 0, EntityMetaTypes.VAR_INT);
	protected static final MetaDataField<Vector3f> META_TRANSLATION = new MetaDataField<>(CoreEntity.LAST_META_INDEX+4, new Vector3f(), EntityMetaTypes.VECTOR_3F);
	protected static final MetaDataField<Vector3f> META_SCALE = new MetaDataField<>(CoreEntity.LAST_META_INDEX+5, new Vector3f(1f,1f,1f), EntityMetaTypes.VECTOR_3F);
	protected static final MetaDataField<Quaternionf> META_LEFT_ROTATION = new MetaDataField<>(CoreEntity.LAST_META_INDEX+6, new Quaternionf(), EntityMetaTypes.QUATERNION_F);
	protected static final MetaDataField<Quaternionf> META_RIGHT_ROTATION = new MetaDataField<>(CoreEntity.LAST_META_INDEX+7, new Quaternionf(), EntityMetaTypes.QUATERNION_F);
	protected static final MetaDataField<Byte> META_BILLBOARD = new MetaDataField<>(CoreEntity.LAST_META_INDEX+8, (byte) Billboard.FIXED.getID(), EntityMetaTypes.BYTE); 
	protected static final MetaDataField<Integer> META_BRIGHTNESS_OVERRIDE = new MetaDataField<>(CoreEntity.LAST_META_INDEX+9, -1, EntityMetaTypes.VAR_INT);
	protected static final MetaDataField<Float> META_VIEW_RANGE = new MetaDataField<>(CoreEntity.LAST_META_INDEX+10, 1f, EntityMetaTypes.FLOAT);
	protected static final MetaDataField<Float> META_SHADOW_RADIUS = new MetaDataField<>(CoreEntity.LAST_META_INDEX+11, 0f, EntityMetaTypes.FLOAT);
	protected static final MetaDataField<Float> META_SHADOW_STRENGTH = new MetaDataField<>(CoreEntity.LAST_META_INDEX+12, 1f, EntityMetaTypes.FLOAT);
	protected static final MetaDataField<Float> META_WIDTH = new MetaDataField<>(CoreEntity.LAST_META_INDEX+13, 0f, EntityMetaTypes.FLOAT);
	protected static final MetaDataField<Float> META_HEIGHT = new MetaDataField<>(CoreEntity.LAST_META_INDEX+14, 0f, EntityMetaTypes.FLOAT);
	protected static final MetaDataField<Integer> META_GLOW_COLOR_OVERRIDE = new MetaDataField<>(CoreEntity.LAST_META_INDEX+15, -1, EntityMetaTypes.VAR_INT);
	
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
		metaContainer.setData(META_BRIGHTNESS_OVERRIDE, val);
	}

	@Override
	public boolean hasBrightness() {
		return metaContainer.getData(META_BRIGHTNESS_OVERRIDE) != -1;
	}

	@Override
	public Billboard getBillboard() { 
		return EnumUtil.getByID(Billboard.class, metaContainer.getData(META_BILLBOARD));
	}

	@Override
	public void setBillboard(Billboard billboard) {
		metaContainer.setData(META_BILLBOARD, (byte) billboard.getID());
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
		metaContainer.setData(META_GLOW_COLOR_OVERRIDE, val);
	}

	@Override
	public float getDisplayWidth() {
		return metaContainer.getData(META_WIDTH).intValue();
	}

	@Override
	public void setDisplayWidth(float width) {
		metaContainer.setData(META_WIDTH, width);
	}

	@Override
	public float getDisplayHeight() {
		return metaContainer.getData(META_HEIGHT).intValue();
	}

	@Override
	public void setDisplayHeight(float height) {
		metaContainer.setData(META_HEIGHT, height);
	}

	@Override
	public int getTransformationInterpolationDuration() {
		return metaContainer.getData(META_TRANSFORMATION_INTERPOLATION_DURATION);
	}

	@Override
	public void setTransformationInterpolationDuration(int duration) {
		metaContainer.setData(META_TRANSFORMATION_INTERPOLATION_DURATION, duration);
	}

	@Override
	public int getInterpolationDelay() {
		return metaContainer.getData(META_INTERPOLATION_DELAY);
	}

	@Override
	public void setInterpolationDelay(int delay) {
		metaContainer.setData(META_INTERPOLATION_DELAY, delay);
	}

	@Override
	public float getShadowRadius() {
		return metaContainer.getData(META_SHADOW_RADIUS);
	}

	@Override
	public void setShadowRadius(float radius) {
		metaContainer.setData(META_SHADOW_RADIUS, radius);
	}

	@Override
	public float getShadowStrength() {
		return metaContainer.getData(META_SHADOW_STRENGTH);
	}

	@Override
	public void setShadowStrength(float strength) {
		metaContainer.setData(META_SHADOW_STRENGTH, strength);
	}

	@Override
	public float getViewRange() {
		return metaContainer.getData(META_VIEW_RANGE);
	}

	@Override
	public void setViewRange(float range) {
		metaContainer.setData(META_VIEW_RANGE, range);
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
		var metaContainer = this.metaContainer;
		if (transformation == null) {
			metaContainer.resetData(META_SCALE);
			metaContainer.resetData(META_TRANSLATION);
			metaContainer.resetData(META_RIGHT_ROTATION);
			metaContainer.resetData(META_LEFT_ROTATION);
		} else {
			metaContainer.setData(META_SCALE, transformation.getScale());
			metaContainer.setData(META_TRANSLATION, transformation.getTranslation());
			metaContainer.setData(META_RIGHT_ROTATION, transformation.getRotationRight());
			metaContainer.setData(META_LEFT_ROTATION, transformation.getRotationLeft());
		}
	}

	@Override
	public int getPositionInterpolationDuration() {
		return metaContainer.getData(META_POSITION_INTERPOLATION_DURATION);
	}

	@Override
	public void setPositionInterpolationDuration(int duration) {
		metaContainer.setData(META_POSITION_INTERPOLATION_DURATION, duration);
	}

}
