package de.atlasmc.entity;

import de.atlasmc.block.data.BlockData;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.sound.EnumSound;
import de.atlasmc.sound.Sound;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface AbstractArrow extends Projectile {
	
	public static final NBTSerializationHandler<AbstractArrow>
	NBT_HANDLER = NBTSerializationHandler
					.builder(AbstractArrow.class)
					.include(Projectile.NBT_HANDLER)
					.boolField("crit", AbstractArrow::isCritical, AbstractArrow::setCritical, false)
					.doubleField("damage", AbstractArrow::getDamage, AbstractArrow::setDamage, 2)
					.typeComponentField("inBlockState", AbstractArrow::getBlockDataIn, AbstractArrow::setBlockDataIn, BlockData.NBT_HANDLER)
					.boolField("inGround", AbstractArrow::isInGround, AbstractArrow::setInGround, false)
					.shortField("life", AbstractArrow::getLifeTime, AbstractArrow::setLifeTime, (short) 1200)
					.boolField("pickup", AbstractArrow::isPickupable, AbstractArrow::setPickupable, true)
					.byteField("PierceLevel", AbstractArrow::getPiercingLevel, AbstractArrow::setPiercingLevel, (byte) 0)
					.byteField("shake", AbstractArrow::getShake, AbstractArrow::setShake, (byte) 0)
					.boolField("ShotFromCrossbow", AbstractArrow::isShotFromCrossbow, AbstractArrow::setShotFromCrossbow)
					.addField(Sound.getNBTSoundField("SoundEvent", AbstractArrow::getHitSound, AbstractArrow::setHitSound, EnumSound.ENTITY_ARROW_HIT))
					.typeComponentField("item", AbstractArrow::getItem, AbstractArrow::setItem, ItemStack.NBT_HANDLER)
					.typeComponentField("weapon", AbstractArrow::getWeapon, AbstractArrow::setWeapon, ItemStack.NBT_HANDLER)
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
	default NBTSerializationHandler<? extends AbstractArrow> getNBTHandler() {
		return NBT_HANDLER;
	}
	
}
