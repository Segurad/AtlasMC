package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.ArmorStand;
import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.data.MetaData;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.util.EulerAngle;
import de.atlasmc.world.World;

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
	
	public CoreArmorStand(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
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

}
