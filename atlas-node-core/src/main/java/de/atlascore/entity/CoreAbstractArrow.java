package de.atlascore.entity;

import de.atlasmc.block.data.BlockData;
import de.atlasmc.entity.AbstractArrow;
import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.data.MetaData;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.sound.EnumSound;
import de.atlasmc.sound.Sound;

public abstract class CoreAbstractArrow extends CoreAbstractProjectile implements AbstractArrow {

	protected static final MetaDataField<Byte> 
	META_ABSTRACT_ARROW_FLAGS = new MetaDataField<>(CoreEntity.LAST_META_INDEX+1, (byte) 0, MetaDataType.BYTE);
	protected static final MetaDataField<Byte>
	META_PIERCING_LEVEL = new MetaDataField<>(CoreEntity.LAST_META_INDEX+2, (byte) 0, MetaDataType.BYTE);
	
	protected static final int LAST_META_INDEX = CoreEntity.LAST_META_INDEX+2;
	
	private double damage = 2;
	private boolean pickupable; 
	private int lifetime = 1200;
	private byte shake;
	private boolean inGround;
	private BlockData blockDataIn;
	private boolean shotFromCrossbow;
	private Sound hitSound = EnumSound.ENTITY_ARROW_HIT;
	private ItemStack item;
	private ItemStack weapon;
	
	public CoreAbstractArrow(EntityType type) {
		super(type);
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_ABSTRACT_ARROW_FLAGS);
		metaContainer.set(META_PIERCING_LEVEL);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}

	@Override
	public boolean isCritical() {
		return (metaContainer.getData(META_ABSTRACT_ARROW_FLAGS) & 0x1) == 0x1;
	}

	@Override
	public void setCritical(boolean critical) {
		MetaData<Byte> data = metaContainer.get(META_ABSTRACT_ARROW_FLAGS);
		data.setData((byte) (data.getData() | 0x1));
	}
	
	@Override
	public int getPiercingLevel() {
		return metaContainer.getData(META_PIERCING_LEVEL);
	}
	
	@Override
	public void setPiercingLevel(int level) {
		metaContainer.get(META_PIERCING_LEVEL).setData((byte) level);
	}
	
	@Override
	public void setDamage(double damage) {
		this.damage = damage;
	}
	
	@Override
	public double getDamage() {
		return damage;
	}

	@Override
	public void setLifeTime(int ticks) {
		this.lifetime = ticks;
	}
	
	@Override
	public int getLifeTime() {
		return lifetime;
	}

	@Override
	public void setPickupable(boolean pickupable) {
		this.pickupable = pickupable;
	}
	
	@Override
	public boolean isPickupable() {
		return pickupable;
	}

	@Override
	public ItemStack getItem() {
		return item;
	}

	@Override
	public void setItem(ItemStack item) {
		this.item = item;
	}

	@Override
	public ItemStack getWeapon() {
		return weapon;
	}

	@Override
	public void setWeapon(ItemStack weapon) {
		this.weapon = weapon;
	}

	@Override
	public Sound getHitSound() {
		return hitSound;
	}

	@Override
	public void setHitSound(Sound sound) {
		this.hitSound = sound;
	}

	@Override
	public boolean isShotFromCrossbow() {
		return shotFromCrossbow;
	}

	@Override
	public void setShotFromCrossbow(boolean value) {
		this.shotFromCrossbow = value;
	}

	@Override
	public BlockData getBlockDataIn() {
		return blockDataIn;
	}

	@Override
	public void setBlockDataIn(BlockData data) {
		this.blockDataIn = data;
	}

	@Override
	public boolean isInGround() {
		return inGround;
	}

	@Override
	public void setInGround(boolean inGround) {
		this.inGround = inGround;
	}

	@Override
	public int getShake() {
		return shake;
	}

	@Override
	public void setShake(int shake) {
		this.shake = (byte) shake;
	}

}
