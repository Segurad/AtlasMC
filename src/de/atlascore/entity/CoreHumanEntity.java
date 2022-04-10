package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.Entity;
import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.HumanEntity;
import de.atlasmc.entity.Player;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.event.inventory.InventoryType;
import de.atlasmc.factory.ContainerFactory;
import de.atlasmc.inventory.MainHand;
import de.atlasmc.inventory.PlayerInventory;
import de.atlasmc.util.nbt.tag.CompoundTag;
import de.atlasmc.world.World;

public class CoreHumanEntity extends CoreLivingEntity implements HumanEntity {

	protected static final MetaDataField<Float>
	META_ADDITIONAL_HEARTS = new MetaDataField<>(CoreLivingEntity.LAST_META_INDEX+1, 0.0f, MetaDataType.FLOAT);
	protected static final MetaDataField<Integer>
	META_SCORE = new MetaDataField<>(CoreLivingEntity.LAST_META_INDEX+2, 0, MetaDataType.INT);
	protected static final MetaDataField<Byte>
	META_SKIN_SETTINGS = new MetaDataField<>(CoreLivingEntity.LAST_META_INDEX+3, (byte) 0, MetaDataType.BYTE);
	protected static final MetaDataField<Byte>
	META_MAIN_HAND = new MetaDataField<>(CoreLivingEntity.LAST_META_INDEX+4, (byte) 1, MetaDataType.BYTE);
	protected static final MetaDataField<CompoundTag>
	META_SHOULDER_LEFT = new MetaDataField<>(CoreLivingEntity.LAST_META_INDEX+5, null, MetaDataType.NBT_DATA);
	protected static final MetaDataField<CompoundTag>
	META_SHOULDER_RIGHT = new MetaDataField<>(CoreLivingEntity.LAST_META_INDEX+6, null, MetaDataType.NBT_DATA);
	
	protected static final int LAST_META_INDEX = CoreLivingEntity.LAST_META_INDEX+6;
	
	private PlayerInventory inv;
	private double additionalHealth;
	private Entity shoulderRight, shoulderLeft;
	private byte shoulderChanged; // 0x01 Right 0x02 Left
	
	public CoreHumanEntity(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
		inv = ContainerFactory.PLAYER_INV_FACTORY.create(InventoryType.PLAYER, this);
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_ADDITIONAL_HEARTS);
		metaContainer.set(META_SCORE);
		metaContainer.set(META_SKIN_SETTINGS);
		metaContainer.set(META_MAIN_HAND);
		metaContainer.set(META_SHOULDER_LEFT);
		metaContainer.set(META_SHOULDER_RIGHT);
	}

	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}
	
	@Override
	public PlayerInventory getInventory() {
		return inv;
	}

	@Override
	public double getAdditionHearts() {
		return additionalHealth;
	}

	@Override
	public void setAdditionHearts(double value) {
		this.additionalHealth = value;
	}

	@Override
	public int getDisplayedSkinParts() {
		return metaContainer.getData(META_SKIN_SETTINGS) & 0xFF;
	}

	@Override
	public void setDisplayedSkinParts(int parts) {
		metaContainer.get(META_SKIN_SETTINGS).setData((byte) parts);
	}

	@Override
	public MainHand getMainHand() {
		return MainHand.getByID(metaContainer.getData(META_MAIN_HAND));
	}

	@Override
	public void setMainHand(MainHand hand) {
		if (hand == null)
			throw new IllegalArgumentException("Hand can not be null!");
		metaContainer.get(META_MAIN_HAND).setData((byte) hand.getID());
	}

	@Override
	public Entity getRightShoulder() {
		return shoulderRight;
	}

	@Override
	public void setRightShoulder(Entity entity) {
		if (shoulderRight == entity)
			return;
		if (entity instanceof Player)
			throw new IllegalArgumentException("Player can not be a shoulder pet!");
		shoulderRight = entity;
		if (entity != null)
			entity.remove();
		shoulderChanged |= 0x01;
	}

	@Override
	public Entity getLeftShoulder() {
		return shoulderLeft;
	}

	@Override
	public void setLeftShoulder(Entity entity) {
		if (shoulderLeft == entity)
			return;
		if (entity instanceof Player)
			throw new IllegalArgumentException("Player can not be a shoulder pet!");
		shoulderLeft = entity;
		if (entity != null)
			entity.remove();
		shoulderChanged |= 0x02;
	}
	
	@Override
	protected void prepUpdate() {
		super.prepUpdate();
		if (shoulderChanged != 0) {
			if ((shoulderChanged & 0x01) == 0x01) {
				prepShoulder(META_SHOULDER_RIGHT, shoulderRight);
			}
			if ((shoulderChanged & 0x02) == 0x02) {
				prepShoulder(META_SHOULDER_LEFT, shoulderLeft);
			}
			shoulderChanged = 0;
		}
	}
	
	private void prepShoulder(MetaDataField<CompoundTag> shoulder, Entity entity) {
		if (entity == null)
			metaContainer.get(shoulder).setData(null);
		else
			metaContainer.get(shoulder).setData((CompoundTag) entity.toNBT());
	}

}
