package de.atlasmc.node.entity;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.node.block.data.BlockData;
import de.atlasmc.node.inventory.ItemStack;
import de.atlasmc.node.sound.EnumSound;
import de.atlasmc.node.sound.Sound;

public interface AbstractArrow extends Projectile {
	
	public static final NBTCodec<AbstractArrow>
	NBT_HANDLER = NBTCodec
					.builder(AbstractArrow.class)
					.include(Projectile.NBT_HANDLER)
					.boolField("crit", AbstractArrow::isCritical, AbstractArrow::setCritical, false)
					.doubleField("damage", AbstractArrow::getDamage, AbstractArrow::setDamage, 2)
					.codec("inBlockState", AbstractArrow::getBlockDataIn, AbstractArrow::setBlockDataIn, BlockData.NBT_CODEC)
					.boolField("inGround", AbstractArrow::isInGround, AbstractArrow::setInGround, false)
					.shortField("life", AbstractArrow::getLifeTime, AbstractArrow::setLifeTime, (short) 1200)
					.boolField("pickup", AbstractArrow::isPickupable, AbstractArrow::setPickupable, true)
					.byteField("PierceLevel", AbstractArrow::getPiercingLevel, AbstractArrow::setPiercingLevel, (byte) 0)
					.byteField("shake", AbstractArrow::getShake, AbstractArrow::setShake, (byte) 0)
					.boolField("ShotFromCrossbow", AbstractArrow::isShotFromCrossbow, AbstractArrow::setShotFromCrossbow)
					.codec("SoundEvent", AbstractArrow::getHitSound, AbstractArrow::setHitSound, Sound.NBT_CODEC, EnumSound.ENTITY_ARROW_HIT)
					.codec("item", AbstractArrow::getItem, AbstractArrow::setItem, ItemStack.NBT_HANDLER)
					.codec("weapon", AbstractArrow::getWeapon, AbstractArrow::setWeapon, ItemStack.NBT_HANDLER)
					.build();
	
	ItemStack getItem();
	
	void setItem(ItemStack item);
	
	ItemStack getWeapon();
	
	void setWeapon(ItemStack weapon);
	
	Sound getHitSound();
	
	void setHitSound(Sound sound);
	
	boolean isShotFromCrossbow();
	
	void setShotFromCrossbow(boolean value);
	
	boolean isCritical();
	
	void setCritical(boolean critical);
	
	BlockData getBlockDataIn();
	
	void setBlockDataIn(BlockData data);
	
	boolean isInGround();
	
	void setInGround(boolean inGround);
	
	int getPiercingLevel();
	
	/**
	 * Sets the piercing level of this projectile (values higher than 0xFF are ignored)
	 * @param level of piercing
	 */
	void setPiercingLevel(int level);

	void setDamage(double damage);
	
	double getDamage();

	void setLifeTime(int ticks);
	
	/**
	 * Returns the time in ticks until this Arrow despawns or -1 if not counting
	 * @return ticks or -1
	 */
	int getLifeTime();

	void setPickupable(boolean pickupable);
	
	boolean isPickupable();
	
	int getShake();
	
	void setShake(int shake);
	
	@Override
	default NBTCodec<? extends AbstractArrow> getNBTCodec() {
		return NBT_HANDLER;
	}
	
}
