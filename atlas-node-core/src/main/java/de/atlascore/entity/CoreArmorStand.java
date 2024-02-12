package de.atlascore.entity;

import java.io.IOException;
import java.util.UUID;

import de.atlasmc.entity.ArmorStand;
import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.data.MetaData;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.util.EulerAngle;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.ChildNBTFieldContainer;
import de.atlasmc.util.nbt.NBTFieldContainer;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreArmorStand extends CoreLivingEntity implements ArmorStand {

	/**
	 * <table>
	 * <tr><th>Bit</th><th>Description</th></tr>
	 * <tr><td>0x01</td><td>Is Small</td></tr>
	 * <tr><td>0x04</td><td>Has Arms</td></tr>
	 * <tr><td>0x08</td><td>Has no BasePlate</td></tr>
	 * <tr><td>0x10</td><td>is Marker</td></tr>
	 * </table>
	 */
	protected static final MetaDataField<Byte>
	META_ARMOR_STAND_FLAGS = new MetaDataField<>(CoreLivingEntity.LAST_META_INDEX+1, (byte) 0, MetaDataType.BYTE);
	protected static final MetaDataField<EulerAngle>
	META_ROTATION_HEAD = new MetaDataField<>(CoreLivingEntity.LAST_META_INDEX+2, new EulerAngle(), MetaDataType.ROTATION);
	protected static final MetaDataField<EulerAngle>
	META_ROTATION_BODY = new MetaDataField<>(CoreLivingEntity.LAST_META_INDEX+3, new EulerAngle(), MetaDataType.ROTATION);
	protected static final MetaDataField<EulerAngle>
	META_ROTATION_LEFT_ARM = new MetaDataField<>(CoreLivingEntity.LAST_META_INDEX+4, new EulerAngle(-10.0f, 0.0f, -10.0f), MetaDataType.ROTATION);
	protected static final MetaDataField<EulerAngle>
	META_ROTATION_RIGHT_ARM = new MetaDataField<>(CoreLivingEntity.LAST_META_INDEX+5, new EulerAngle(-15.0f, 0.0f, 10.0f), MetaDataType.ROTATION);
	protected static final MetaDataField<EulerAngle>
	META_ROTATION_LEFT_LEG = new MetaDataField<>(CoreLivingEntity.LAST_META_INDEX+6, new EulerAngle(-1.0f, 0.0f, -1.0f), MetaDataType.ROTATION);
	protected static final MetaDataField<EulerAngle>
	META_ROTATION_RIGHT_LEG = new MetaDataField<>(CoreLivingEntity.LAST_META_INDEX+7, new EulerAngle(1.0f, 0.0f, 1.0f), MetaDataType.ROTATION);
	
	protected static final int LAST_META_INDEX = CoreLivingEntity.LAST_META_INDEX+7;
	
	protected static final NBTFieldContainer<CoreArmorStand> NBT_FIELDS;
	
	protected static final CharKey
	NBT_DISABLED_SLOTS = CharKey.literal("DisabledSlots"),
	NBT_INVISIBLE = CharKey.literal("Invisible"),
	NBT_MARKER = CharKey.literal("Marker"),
	NBT_NO_BASE_PLATE = CharKey.literal("NoBasePlate"),
	NBT_POSE = CharKey.literal("Pose"),
	NBT_HEAD = CharKey.literal("Head"),
	NBT_BODY = CharKey.literal("Body"),
	NBT_LEFT_ARM = CharKey.literal("LeftArm"),
	NBT_RIGHT_ARM = CharKey.literal("RightArm"),
	NBT_LEFT_LEG = CharKey.literal("LeftLeg"),
	NBT_RIGHT_LEG = CharKey.literal("RightLeg"),
	NBT_SHOW_ARMS = CharKey.literal("ShowArms"),
	NBT_SMALL = CharKey.literal("Small");
	
	static {
		NBT_FIELDS = new ChildNBTFieldContainer<>(CoreLivingEntity.NBT_FIELDS);
		NBT_FIELDS.setField(NBT_DISABLED_SLOTS, (holder, reader) -> {
			holder.setSlotInteractionFlags(reader.readIntTag());
		});
		NBT_FIELDS.setField(NBT_INVISIBLE, (holder, reader) -> {
			holder.setInvisible(reader.readByteTag() == 1);
		});
		NBT_FIELDS.setField(NBT_MARKER, (holder, reader) -> {
			holder.setMarker(reader.readByteTag() == 1);
		});
		NBT_FIELDS.setField(NBT_NO_BASE_PLATE, (holder, reader) -> {
			holder.setBasePlate(reader.readByteTag() == 0);
		});
		NBT_FIELDS.setField(NBT_POSE, (holder, reader) -> {
			reader.readNextEntry();
			while (reader.getType() != TagType.TAG_END) {
				if (reader.getType() != TagType.LIST)
					reader.skipTag();
				float x;
				float y;
				float z;
				final CharSequence fieldName = reader.getFieldName();
				reader.readNextEntry();
				x = reader.readFloatTag();
				y = reader.readFloatTag();
				z = reader.readFloatTag();
				if (NBT_HEAD.equals(fieldName))
					holder.setHeadPose(x, y, z);
				else if (NBT_BODY.equals(fieldName))
					holder.setBodyPose(x, y, z);
				else if (NBT_LEFT_ARM.equals(fieldName))
					holder.setLeftArmPose(x, y, z);
				else if (NBT_RIGHT_ARM.equals(fieldName))
					holder.setRightArmPose(x, y, z);
				else if (NBT_LEFT_LEG.equals(fieldName))
					holder.setLeftLegPose(x, y, z);
				else if (NBT_RIGHT_LEG.equals(fieldName))
					holder.setRightLegPose(x, y, z);
			}
			reader.readNextEntry();
		});
		NBT_FIELDS.setField(NBT_SHOW_ARMS, (holder, reader) -> {
			holder.setArms(reader.readByteTag() == 1);
		});
		NBT_FIELDS.setField(NBT_SMALL, (holder, reader) -> {
			holder.setSmall(reader.readByteTag() == 1);
		});
	}
	
	private int slotFlags;
	
	public CoreArmorStand(EntityType type, UUID uuid) {
		super(type, uuid);
	}
	
	@Override
	protected NBTFieldContainer<? extends CoreArmorStand> getFieldContainerRoot() {
		return NBT_FIELDS;
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_ARMOR_STAND_FLAGS);
		metaContainer.set(META_ROTATION_HEAD, new EulerAngle(META_ROTATION_HEAD.getDefaultData()));
		metaContainer.set(META_ROTATION_BODY, new EulerAngle(META_ROTATION_BODY.getDefaultData()));
		metaContainer.set(META_ROTATION_LEFT_ARM, new EulerAngle(META_ROTATION_LEFT_ARM.getDefaultData()));
		metaContainer.set(META_ROTATION_RIGHT_ARM, new EulerAngle(META_ROTATION_RIGHT_ARM.getDefaultData()));
		metaContainer.set(META_ROTATION_LEFT_LEG, new EulerAngle(META_ROTATION_LEFT_LEG.getDefaultData()));
		metaContainer.set(META_ROTATION_RIGHT_LEG, new EulerAngle(META_ROTATION_RIGHT_LEG.getDefaultData()));
	}

	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}
	
	@Override
	public void setBodyPose(EulerAngle angle) {
		setRotation(META_ROTATION_BODY, angle);
	}

	@Override
	public void setHeadPose(EulerAngle angle) {
		setRotation(META_ROTATION_HEAD, angle);
	}

	@Override
	public void setLeftArmPose(EulerAngle angle) {
		setRotation(META_ROTATION_LEFT_ARM, angle);		
	}

	@Override
	public void setLeftLegPose(EulerAngle angle) {
		setRotation(META_ROTATION_LEFT_LEG, angle);		
	}

	@Override
	public void setRightArmPose(EulerAngle angle) {
		setRotation(META_ROTATION_RIGHT_ARM, angle);
	}

	@Override
	public void setRightLegPose(EulerAngle angle) {
		setRotation(META_ROTATION_RIGHT_LEG, angle);
	}

	@Override
	public EulerAngle getBodyPose(EulerAngle angle) {
		metaContainer.getData(META_ROTATION_BODY).copyTo(angle);
		return angle;
	}

	@Override
	public EulerAngle getHeadPose(EulerAngle angle) {
		metaContainer.getData(META_ROTATION_HEAD).copyTo(angle);
		return angle;
	}

	@Override
	public EulerAngle getLeftArmPose(EulerAngle angle) {
		metaContainer.getData(META_ROTATION_LEFT_ARM).copyTo(angle);
		return angle;
	}

	@Override
	public EulerAngle getLeftLegPose(EulerAngle angle) {
		metaContainer.getData(META_ROTATION_LEFT_LEG).copyTo(angle);
		return angle;
	}

	@Override
	public EulerAngle getRightArmPose(EulerAngle angle) {
		metaContainer.getData(META_ROTATION_RIGHT_ARM).copyTo(angle);
		return angle;
	}

	@Override
	public EulerAngle getRightLegPose(EulerAngle angle) {
		metaContainer.getData(META_ROTATION_RIGHT_LEG).copyTo(angle);
		return angle;
	}
	
	private void setRotation(MetaDataField<EulerAngle> field, EulerAngle angle) {
		if (angle == null)
			throw new IllegalArgumentException("Angle can not be null!");
		MetaData<EulerAngle> data = metaContainer.get(field);
		data.getData().set(angle);
		data.setChanged(true);
	}
	
	private void setRotation(MetaDataField<EulerAngle> field, float x, float y, float z) {
		MetaData<EulerAngle> data = metaContainer.get(field);
		data.getData().set(x, y, z);
		data.setChanged(true);
	}

	@Override
	public boolean isSmall() {
		return (metaContainer.getData(META_ARMOR_STAND_FLAGS) & 0x01) == 0x01;
	}

	@Override
	public void setSmall(boolean small) {
		MetaData<Byte> data = metaContainer.get(META_ARMOR_STAND_FLAGS);
		data.setData((byte) (small ? data.getData() | 0x01 : data.getData() & 0xFE));
	}

	@Override
	public boolean hasArms() {
		return (metaContainer.getData(META_ARMOR_STAND_FLAGS) & 0x04) == 0x04;
	}

	@Override
	public void setArms(boolean arms) {
		MetaData<Byte> data = metaContainer.get(META_ARMOR_STAND_FLAGS);
		data.setData((byte) (arms ? data.getData() | 0x04 : data.getData() & 0xFB));
	}

	@Override
	public boolean hasBasePlate() {
		return (metaContainer.getData(META_ARMOR_STAND_FLAGS) & 0x08) == 0x00;
	}

	@Override
	public void setBasePlate(boolean baseplate) {
		MetaData<Byte> data = metaContainer.get(META_ARMOR_STAND_FLAGS);
		data.setData((byte) (!baseplate ? data.getData() | 0x08 : data.getData() & 0xF7));
	}

	@Override
	public boolean isMarker() {
		return (metaContainer.getData(META_ARMOR_STAND_FLAGS) & 0x10) == 0x10;
	}

	@Override
	public void setMarker(boolean marker) {
		MetaData<Byte> data = metaContainer.get(META_ARMOR_STAND_FLAGS);
		data.setData((byte) (marker ? data.getData() | 0x10 : data.getData() & 0xEF));
	}

	@Override
	public void setSlotInteractionFlags(int flags) {
		this.slotFlags = flags;
	}

	@Override
	public int getSlotInteractionFlags() {
		return slotFlags;
	}

	@Override
	public void setBodyPose(float x, float y, float z) {
		setRotation(META_ROTATION_BODY, x, y, z);
	}

	@Override
	public void setHeadPose(float x, float y, float z) {
		setRotation(META_ROTATION_HEAD, x, y, z);
	}

	@Override
	public void setLeftArmPose(float x, float y, float z) {
		setRotation(META_ROTATION_LEFT_ARM, x, y, z);
	}

	@Override
	public void setLeftLegPose(float x, float y, float z) {
		setRotation(META_ROTATION_LEFT_LEG, x, y, z);		
	}

	@Override
	public void setRightArmPose(float x, float y, float z) {
		setRotation(META_ROTATION_RIGHT_ARM, x, y, z);
	}

	@Override
	public void setRightLegPose(float x, float y, float z) {
		setRotation(META_ROTATION_RIGHT_LEG, x, y, z);
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		writer.writeIntTag(NBT_DISABLED_SLOTS, getSlotInteractionFlags());
		if (isInvisible())
			writer.writeByteTag(NBT_INVISIBLE, true);
		if (isMarker())
			writer.writeByteTag(NBT_MARKER, true);
		if (!hasBasePlate())
			writer.writeByteTag(NBT_NO_BASE_PLATE, true);
		writer.writeCompoundTag(NBT_POSE);
		EulerAngle pose = null;
		if (!metaContainer.get(META_ROTATION_HEAD).isDefault()) {
			pose = metaContainer.getData(META_ROTATION_HEAD);
			writer.writeListTag(NBT_HEAD, TagType.FLOAT, 3);
			writer.writeFloatTag(null, pose.getX());
			writer.writeFloatTag(null, pose.getY());
			writer.writeFloatTag(null, pose.getZ());
		}
		if (!metaContainer.get(META_ROTATION_BODY).isDefault()) {
			pose = metaContainer.getData(META_ROTATION_BODY);
			writer.writeListTag(NBT_BODY, TagType.FLOAT, 3);
			writer.writeFloatTag(null, pose.getX());
			writer.writeFloatTag(null, pose.getY());
			writer.writeFloatTag(null, pose.getZ());
		}
		if (!metaContainer.get(META_ROTATION_LEFT_ARM).isDefault()) {
			pose = metaContainer.getData(META_ROTATION_LEFT_ARM);
			writer.writeListTag(NBT_LEFT_ARM, TagType.FLOAT, 3);
			writer.writeFloatTag(null, pose.getX());
			writer.writeFloatTag(null, pose.getY());
			writer.writeFloatTag(null, pose.getZ());
		}
		if (!metaContainer.get(META_ROTATION_RIGHT_ARM).isDefault()) {
			pose = metaContainer.getData(META_ROTATION_RIGHT_ARM);
			writer.writeListTag(NBT_RIGHT_ARM, TagType.FLOAT, 3);
			writer.writeFloatTag(null, pose.getX());
			writer.writeFloatTag(null, pose.getY());
			writer.writeFloatTag(null, pose.getZ());
		}
		if (!metaContainer.get(META_ROTATION_LEFT_LEG).isDefault()) {
			pose = metaContainer.getData(META_ROTATION_LEFT_LEG);
			writer.writeListTag(NBT_LEFT_LEG, TagType.FLOAT, 3);
			writer.writeFloatTag(null, pose.getX());
			writer.writeFloatTag(null, pose.getY());
			writer.writeFloatTag(null, pose.getZ());
		}
		if (!metaContainer.get(META_ROTATION_RIGHT_LEG).isDefault()) {
			pose = metaContainer.getData(META_ROTATION_RIGHT_LEG);
			writer.writeListTag(NBT_RIGHT_LEG, TagType.FLOAT, 3);
			writer.writeFloatTag(null, pose.getX());
			writer.writeFloatTag(null, pose.getY());
			writer.writeFloatTag(null, pose.getZ());
		}
		if (hasArms())
			writer.writeByteTag(NBT_SHOW_ARMS, true);
		if (isSmall())
			writer.writeByteTag(NBT_SMALL, true);
	}

}
