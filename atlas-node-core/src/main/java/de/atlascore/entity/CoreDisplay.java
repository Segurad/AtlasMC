package de.atlascore.entity;

import java.io.IOException;
import java.util.UUID;

import org.joml.Quaternionf;
import org.joml.Vector3f;

import de.atlasmc.Color;
import de.atlasmc.entity.Display;
import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.data.MetaData;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.NBTFieldContainer;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTWriter;

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
	
	protected static final NBTFieldContainer<CoreDisplay> NBT_FIELDS;
	
	private static final CharKey
	NBT_BILLBOARD = CharKey.literal("billboard"),
	NBT_BRIGHTNESS = CharKey.literal("brightness"),
		NBT_BLOCK = CharKey.literal("block"),
		NBT_SKY = CharKey.literal("sky"),
	NBT_GLOW_COLOR_OVERRIDE = CharKey.literal("glow_color_override"),
	NBT_HEIGHT = CharKey.literal("height"),
	NBT_WIDTH = CharKey.literal("width"),
	NBT_INTERPOLATION_DURATION = CharKey.literal("interpolation_duration"),
	NBT_TELEPORT_DURATION = CharKey.literal("teleport_duration"),
	NBT_START_INTERPOLATION = CharKey.literal("start_interpolation"),
	NBT_SHADOW_RADIUS = CharKey.literal("shadow_radius"),
	NBT_SHADOW_STRENGTH = CharKey.literal("shadow_strength"),
	NBT_VIEW_RANGE = CharKey.literal("view_range"),
	NBT_TRANSFORMATION = CharKey.literal("transformation"),
	NBT_RIGHT_ROTATION = CharKey.literal("right_rotation"),
	NBT_LEFT_ROTATION = CharKey.literal("left_rotation"),
	NBT_SCALE = CharKey.literal("scale"),
	NBT_TRANSLATION = CharKey.literal("translation");
	
	static {
		NBT_FIELDS = CoreEntity.NBT_FIELDS.fork();
		NBT_FIELDS.setField(NBT_BILLBOARD, (holder, reader) -> {
			holder.setBillboard(Billboard.getByNameID(reader.readStringTag()));
		});
		NBTFieldContainer<CoreDisplay> transformation = NBT_FIELDS.setContainer(NBT_TRANSFORMATION);
		transformation.setField(NBT_RIGHT_ROTATION, (holder, reader) -> {
			reader.readNextEntry();
			float x = reader.readFloatTag();
			float y = reader.readFloatTag();
			float z = reader.readFloatTag();
			float w = reader.readFloatTag();
			MetaData<Quaternionf> meta = holder.metaContainer.get(META_RIGHT_ROTATION);
			meta.getData().set(x, y, z, w);
			meta.setChanged(true);
		});
		transformation.setField(NBT_LEFT_ROTATION, (holder, reader) -> {
			reader.readNextEntry();
			float x = reader.readFloatTag();
			float y = reader.readFloatTag();
			float z = reader.readFloatTag();
			float w = reader.readFloatTag();
			MetaData<Quaternionf> meta = holder.metaContainer.get(META_RIGHT_ROTATION);
			meta.getData().set(x, y, z, w);
			meta.setChanged(true);
		});
		transformation.setField(NBT_SCALE, (holder, reader) -> {
			reader.readNextEntry();
			float x = reader.readFloatTag();
			float y = reader.readFloatTag();
			float z = reader.readFloatTag();
			MetaData<Vector3f> meta = holder.metaContainer.get(META_SCALE);
			meta.getData().set(x, y, z);
			meta.setChanged(true);
		});
		transformation.setField(NBT_TRANSLATION, (holder, reader) -> {
			reader.readNextEntry();
			float x = reader.readFloatTag();
			float y = reader.readFloatTag();
			float z = reader.readFloatTag();
			MetaData<Vector3f> meta = holder.metaContainer.get(META_TRANSLATION);
			meta.getData().set(x, y, z);
			meta.setChanged(true);
		});
		NBT_FIELDS.setField(NBT_GLOW_COLOR_OVERRIDE, (holder, reader) -> {
			holder.metaContainer.get(META_GLOW_COLOR_OVERRIDE).setData(reader.readIntTag());
		});
		NBT_FIELDS.setField(NBT_WIDTH, (holder, reader) -> {
			holder.setDisplayWidth(reader.readIntTag());
		});
		NBT_FIELDS.setField(NBT_HEIGHT, (holder, reader) -> {
			holder.setDisplayHeight(reader.readIntTag());
		});
		NBT_FIELDS.setField(NBT_INTERPOLATION_DURATION, (holder, reader) -> {
			holder.setTransformationInterpolationDuration(reader.readIntTag());
		});
		NBT_FIELDS.setField(NBT_TELEPORT_DURATION, (holder, reader) -> {
			holder.setPositionInterpolationDuration(reader.readIntTag());
		});
		NBT_FIELDS.setField(NBT_START_INTERPOLATION, (holder, reader) -> {
			holder.setInterpolationDelay(reader.readIntTag());
		});
		NBT_FIELDS.setField(NBT_SHADOW_RADIUS, (holder, reader) -> {
			holder.setShadowRadius(reader.readFloatTag());
		});
		NBT_FIELDS.setField(NBT_SHADOW_STRENGTH, (holder, reader) -> {
			holder.setShadowStrength(reader.readFloatTag());
		});
		NBT_FIELDS.setField(NBT_VIEW_RANGE, (holder, reader) -> {
			holder.setViewRange(reader.readFloatTag());
		});
		NBT_FIELDS.setField(NBT_BRIGHTNESS, (holder, reader) -> {
			reader.readNextEntry();
			CharSequence name = reader.getFieldName();
			int blockLight = 0;
			int skyLight = 0;
			while (reader.getType() != TagType.TAG_END) {
				if (NBT_BLOCK.equals(name)) {
					blockLight = reader.readIntTag();
				} else if (NBT_SKY.equals(name)) {
					skyLight = reader.readIntTag();
				} else {
					reader.skipTag();
				}
			}
			holder.setBrightness(new Brightness(blockLight, skyLight));
		});
	}
	
	public CoreDisplay(EntityType type, UUID uuid) {
		super(type, uuid);
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
	protected NBTFieldContainer<? extends CoreDisplay> getFieldContainerRoot() {
		return NBT_FIELDS;
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
	public int getDisplayWidth() {
		return metaContainer.getData(META_WIDTH).intValue();
	}

	@Override
	public void setDisplayWidth(int width) {
		metaContainer.get(META_WIDTH).setData((float) width);
	}

	@Override
	public int getDisplayHeight() {
		return metaContainer.getData(META_HEIGHT).intValue();
	}

	@Override
	public void setDisplayHeight(int height) {
		metaContainer.get(META_HEIGHT).setData((float) height);
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
		Vector3f scale = new Vector3f(metaContainer.getData(META_SCALE));
		Vector3f translation = new Vector3f(metaContainer.getData(META_TRANSLATION));
		Quaternionf rightRotation = new Quaternionf(metaContainer.getData(META_RIGHT_ROTATION));
		Quaternionf leftRotation = new Quaternionf(metaContainer.getData(META_LEFT_ROTATION));
		return new Transformation(scale, translation, rightRotation, leftRotation);
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
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		int billboard = metaContainer.getData(META_BILLBOARD);
		if (billboard != META_BILLBOARD.getDefaultData())
			writer.writeByteTag(NBT_BILLBOARD, billboard);
		if (hasBrightness()) {
			writer.writeCompoundTag(NBT_BRIGHTNESS);
			Brightness brightness = getBrightness();
			writer.writeIntTag(NBT_BLOCK, brightness.getBlockLightLevel());
			writer.writeIntTag(NBT_SKY, brightness.getSkyLightLevel());
			writer.writeEndTag();
		}
		int interpolationDuration = getTransformationInterpolationDuration();
		if (interpolationDuration != 0)
			writer.writeIntTag(NBT_INTERPOLATION_DURATION, interpolationDuration);
		int positionInterpostaionDuration = getPositionInterpolationDuration();
		if (positionInterpostaionDuration != 0)
			writer.writeIntTag(NBT_TELEPORT_DURATION, positionInterpostaionDuration);
		int interpolationDelay = getInterpolationDelay();
		if (interpolationDelay != 0)
			writer.writeIntTag(NBT_START_INTERPOLATION, interpolationDelay);
		boolean transformation = false;
		MetaData<Vector3f> scale = metaContainer.get(META_SCALE);
		if (!scale.isDefault()) {
			if (!transformation) {
				writer.writeCompoundTag(NBT_TRANSFORMATION);
				transformation = true;
			}
			Vector3f vec = scale.getData();
			writer.writeListTag(NBT_SCALE, TagType.FLOAT, 3);
			writer.writeFloatTag(null, vec.x);
			writer.writeFloatTag(null, vec.y);
			writer.writeFloatTag(null, vec.z);
		}
		MetaData<Vector3f> translation = metaContainer.get(META_TRANSLATION);
		if (!translation.isDefault()) {
			if (!transformation) {
				writer.writeCompoundTag(NBT_TRANSFORMATION);
				transformation = true;
			}
			Vector3f vec = translation.getData();
			writer.writeListTag(NBT_TRANSLATION, TagType.FLOAT, 3);
			writer.writeFloatTag(null, vec.x);
			writer.writeFloatTag(null, vec.y);
			writer.writeFloatTag(null, vec.z);
		}
		MetaData<Quaternionf> rightRotation = metaContainer.get(META_RIGHT_ROTATION);
		if (!rightRotation.isDefault()) {
			if (!transformation) {
				writer.writeCompoundTag(NBT_TRANSFORMATION);
				transformation = true;
			}
			Quaternionf quat = rightRotation.getData();
			writer.writeListTag(NBT_RIGHT_ROTATION, TagType.FLOAT, 4);
			writer.writeFloatTag(null, quat.x);
			writer.writeFloatTag(null, quat.y);
			writer.writeFloatTag(null, quat.z);
			writer.writeFloatTag(null, quat.w);
		}
		MetaData<Quaternionf> leftRotation = metaContainer.get(META_LEFT_ROTATION);
		if (!leftRotation.isDefault()) {
			if (!transformation) {
				writer.writeCompoundTag(NBT_TRANSFORMATION);
				transformation = true;
			}
			Quaternionf quat = leftRotation.getData();
			writer.writeListTag(NBT_LEFT_ROTATION, TagType.FLOAT, 4);
			writer.writeFloatTag(null, quat.x);
			writer.writeFloatTag(null, quat.y);
			writer.writeFloatTag(null, quat.z);
			writer.writeFloatTag(null, quat.w);
		}
		if (transformation)
			writer.writeEndTag();
		float shadowStrength = metaContainer.getData(META_SHADOW_STRENGTH);
		if (shadowStrength != META_SHADOW_STRENGTH.getDefaultData())
			writer.writeFloatTag(NBT_SHADOW_STRENGTH, shadowStrength);
		float shadowRadius = metaContainer.getData(META_SHADOW_RADIUS);
		if (shadowRadius != META_SHADOW_RADIUS.getDefaultData())
			writer.writeFloatTag(NBT_SHADOW_RADIUS, shadowRadius);
		float viewRange = metaContainer.getData(META_VIEW_RANGE);
		if (viewRange != META_VIEW_RANGE.getDefaultData())
			writer.writeFloatTag(NBT_VIEW_RANGE, viewRange);
		int color = metaContainer.getData(META_GLOW_COLOR_OVERRIDE);
		if (color != META_GLOW_COLOR_OVERRIDE.getDefaultData())
			writer.writeIntTag(NBT_GLOW_COLOR_OVERRIDE, color);
		float width = metaContainer.getData(META_WIDTH);
		if (width != META_WIDTH.getDefaultData())
			writer.writeIntTag(NBT_WIDTH, (int) width);
		float height = metaContainer.getData(META_HEIGHT);
		if (height != META_HEIGHT.getDefaultData())
			writer.writeIntTag(NBT_HEIGHT, (int) height);
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
