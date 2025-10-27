package de.atlasmc.core.node.entity;

import org.joml.Vector3f;

import de.atlasmc.node.entity.ArmorStand;
import de.atlasmc.node.entity.EntityType;
import de.atlasmc.node.entity.metadata.type.MetaData;
import de.atlasmc.node.entity.metadata.type.MetaDataField;
import de.atlasmc.node.entity.metadata.type.MetaDataType;

public class CoreArmorStand extends CoreLivingEntity implements ArmorStand {

	
	protected static final int
	FLAG_IS_SMALL = 0x01,
	FLAG_HAS_ARMS = 0x04,
	FLAG_HAS_NO_BASEPLATE = 0x08,
	FLAG_IS_MARKER = 0x10;
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
	protected static final MetaDataField<Vector3f>
	META_ROTATION_HEAD = new MetaDataField<>(CoreLivingEntity.LAST_META_INDEX+2, new Vector3f(), MetaDataType.ROTATION);
	protected static final MetaDataField<Vector3f>
	META_ROTATION_BODY = new MetaDataField<>(CoreLivingEntity.LAST_META_INDEX+3, new Vector3f(), MetaDataType.ROTATION);
	protected static final MetaDataField<Vector3f>
	META_ROTATION_LEFT_ARM = new MetaDataField<>(CoreLivingEntity.LAST_META_INDEX+4, new Vector3f(-10.0f, 0.0f, -10.0f), MetaDataType.ROTATION);
	protected static final MetaDataField<Vector3f>
	META_ROTATION_RIGHT_ARM = new MetaDataField<>(CoreLivingEntity.LAST_META_INDEX+5, new Vector3f(-15.0f, 0.0f, 10.0f), MetaDataType.ROTATION);
	protected static final MetaDataField<Vector3f>
	META_ROTATION_LEFT_LEG = new MetaDataField<>(CoreLivingEntity.LAST_META_INDEX+6, new Vector3f(-1.0f, 0.0f, -1.0f), MetaDataType.ROTATION);
	protected static final MetaDataField<Vector3f>
	META_ROTATION_RIGHT_LEG = new MetaDataField<>(CoreLivingEntity.LAST_META_INDEX+7, new Vector3f(1.0f, 0.0f, 1.0f), MetaDataType.ROTATION);
	
	protected static final int LAST_META_INDEX = CoreLivingEntity.LAST_META_INDEX+7;
	
	private int slotFlags;
	
	public CoreArmorStand(EntityType type) {
		super(type);
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_ARMOR_STAND_FLAGS);
		metaContainer.set(META_ROTATION_HEAD, new Vector3f(META_ROTATION_HEAD.getDefaultData()));
		metaContainer.set(META_ROTATION_BODY, new Vector3f(META_ROTATION_BODY.getDefaultData()));
		metaContainer.set(META_ROTATION_LEFT_ARM, new Vector3f(META_ROTATION_LEFT_ARM.getDefaultData()));
		metaContainer.set(META_ROTATION_RIGHT_ARM, new Vector3f(META_ROTATION_RIGHT_ARM.getDefaultData()));
		metaContainer.set(META_ROTATION_LEFT_LEG, new Vector3f(META_ROTATION_LEFT_LEG.getDefaultData()));
		metaContainer.set(META_ROTATION_RIGHT_LEG, new Vector3f(META_ROTATION_RIGHT_LEG.getDefaultData()));
	}

	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}
	
	@Override
	public void setBodyPose(Vector3f angle) {
		setRotation(META_ROTATION_BODY, angle);
	}

	@Override
	public void setHeadPose(Vector3f angle) {
		setRotation(META_ROTATION_HEAD, angle);
	}

	@Override
	public void setLeftArmPose(Vector3f angle) {
		setRotation(META_ROTATION_LEFT_ARM, angle);		
	}

	@Override
	public void setLeftLegPose(Vector3f angle) {
		setRotation(META_ROTATION_LEFT_LEG, angle);		
	}

	@Override
	public void setRightArmPose(Vector3f angle) {
		setRotation(META_ROTATION_RIGHT_ARM, angle);
	}

	@Override
	public void setRightLegPose(Vector3f angle) {
		setRotation(META_ROTATION_RIGHT_LEG, angle);
	}

	@Override
	public Vector3f getBodyPose(Vector3f angle) {
		angle.set(metaContainer.getData(META_ROTATION_BODY));
		return angle;
	}

	@Override
	public Vector3f getHeadPose(Vector3f angle) {
		angle.set(metaContainer.getData(META_ROTATION_HEAD));
		return angle;
	}

	@Override
	public Vector3f getLeftArmPose(Vector3f angle) {
		angle.set(metaContainer.getData(META_ROTATION_LEFT_ARM));
		return angle;
	}

	@Override
	public Vector3f getLeftLegPose(Vector3f angle) {
		angle.set(metaContainer.getData(META_ROTATION_LEFT_LEG));
		return angle;
	}

	@Override
	public Vector3f getRightArmPose(Vector3f angle) {
		angle.set(metaContainer.getData(META_ROTATION_RIGHT_ARM));
		return angle;
	}

	@Override
	public Vector3f getRightLegPose(Vector3f angle) {
		angle.set(metaContainer.getData(META_ROTATION_RIGHT_LEG));
		return angle;
	}
	
	private void setRotation(MetaDataField<Vector3f> field, Vector3f angle) {
		if (angle == null)
			throw new IllegalArgumentException("Angle can not be null!");
		MetaData<Vector3f> data = metaContainer.get(field);
		data.getData().set(angle);
		data.setChanged(true);
	}
	
	private void setRotation(MetaDataField<Vector3f> field, float x, float y, float z) {
		MetaData<Vector3f> data = metaContainer.get(field);
		data.getData().set(x, y, z);
		data.setChanged(true);
	}

	@Override
	public boolean isSmall() {
		return (metaContainer.getData(META_ARMOR_STAND_FLAGS) & FLAG_IS_SMALL) == FLAG_IS_SMALL;
	}

	@Override
	public void setSmall(boolean small) {
		MetaData<Byte> data = metaContainer.get(META_ARMOR_STAND_FLAGS);
		data.setData((byte) (small ? data.getData() | FLAG_IS_SMALL : data.getData() & ~FLAG_IS_SMALL));
	}

	@Override
	public boolean hasArms() {
		return (metaContainer.getData(META_ARMOR_STAND_FLAGS) & FLAG_HAS_ARMS) == FLAG_HAS_ARMS;
	}

	@Override
	public void setArms(boolean arms) {
		MetaData<Byte> data = metaContainer.get(META_ARMOR_STAND_FLAGS);
		data.setData((byte) (arms ? data.getData() | FLAG_HAS_ARMS : data.getData() & ~FLAG_HAS_ARMS));
	}

	@Override
	public boolean hasNoBasePlate() {
		return (metaContainer.getData(META_ARMOR_STAND_FLAGS) & FLAG_HAS_NO_BASEPLATE) == FLAG_HAS_NO_BASEPLATE;
	}

	@Override
	public void setNoBasePlate(boolean baseplate) {
		MetaData<Byte> data = metaContainer.get(META_ARMOR_STAND_FLAGS);
		data.setData((byte) (!baseplate ? data.getData() | FLAG_HAS_NO_BASEPLATE : data.getData() & ~FLAG_HAS_NO_BASEPLATE));
	}

	@Override
	public boolean isMarker() {
		return (metaContainer.getData(META_ARMOR_STAND_FLAGS) & FLAG_IS_MARKER) == FLAG_IS_MARKER;
	}

	@Override
	public void setMarker(boolean marker) {
		MetaData<Byte> data = metaContainer.get(META_ARMOR_STAND_FLAGS);
		data.setData((byte) (marker ? data.getData() | FLAG_IS_MARKER : data.getData() & ~FLAG_IS_MARKER));
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
	public Vector3f getBodyPoseUnsafe() {
		return metaContainer.getData(META_ROTATION_BODY);
	}

	@Override
	public Vector3f getHeadPoseUnsafe() {
		return metaContainer.getData(META_ROTATION_HEAD);
	}

	@Override
	public Vector3f getLeftArmPoseUnsafe() {
		return metaContainer.getData(META_ROTATION_LEFT_ARM);
	}

	@Override
	public Vector3f getLeftLegPoseUnsafe() {
		return metaContainer.getData(META_ROTATION_LEFT_LEG);
	}

	@Override
	public Vector3f getRightArmPoseUnsafe() {
		return metaContainer.getData(META_ROTATION_RIGHT_ARM);
	}

	@Override
	public Vector3f getRightLegPoseUnsafe() {
		return metaContainer.getData(META_ROTATION_RIGHT_LEG);
	}

}
