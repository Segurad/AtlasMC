package de.atlasmc.node.inventory;

import de.atlasmc.nbt.codec.NBTCodec;

public interface EntityEquipment {
	
	public static final NBTCodec<EntityEquipment>
	NBT_EQUIPMENT_HANDLER = NBTCodec
					.builder(EntityEquipment.class)
					.codec("head", EntityEquipment::getHelmet, EntityEquipment::setHelmet, ItemStack.NBT_CODEC)
					.codec("chest", EntityEquipment::getChestplate, EntityEquipment::setChestplate, ItemStack.NBT_CODEC)
					.codec("legs", EntityEquipment::getLeggings, EntityEquipment::setLeggings, ItemStack.NBT_CODEC)
					.codec("feet", EntityEquipment::getBoots, EntityEquipment::setBoots, ItemStack.NBT_CODEC)
					.codec("mainhand", EntityEquipment::getMainHand, EntityEquipment::setMainHand, ItemStack.NBT_CODEC)
					.codec("offhand", EntityEquipment::getOffHand, EntityEquipment::setOffHand, ItemStack.NBT_CODEC)
					.codec("body", EntityEquipment::getBody, EntityEquipment::setBody, ItemStack.NBT_CODEC)
					.codec("saddle", EntityEquipment::getSaddle, EntityEquipment::setSaddle, ItemStack.NBT_CODEC)
					.build();
	
	public static final NBTCodec<EntityEquipment>
	NBT_DROP_CHANCE_HANDLER = NBTCodec
					.builder(EntityEquipment.class)
					.floatField("head", EntityEquipment::getHelmetDropChance, EntityEquipment::setHelmetDropChance, 1)
					.floatField("chest", EntityEquipment::getChestplateDropChance, EntityEquipment::setChestplateDropChance, 1)
					.floatField("legs", EntityEquipment::getLeggingsDropChance, EntityEquipment::setLeggingsDropChance, 1)
					.floatField("feet", EntityEquipment::getBootsDropChance, EntityEquipment::setBootsDropChance, 1)
					.floatField("mainhand", EntityEquipment::getMainHandDropChance, EntityEquipment::setMainHandDropChance, 1)
					.floatField("offhand", EntityEquipment::getOffHandDropChance, EntityEquipment::setOffHandChance, 1)
					.floatField("body", EntityEquipment::getBodyDropChance, EntityEquipment::setBodyDropChance, 1)
					.floatField("saddle", EntityEquipment::getSaddleDropChance, EntityEquipment::setSaddleDropChance, 1)
					.build();

	void setBootsDropChance(float chance);

	void setLeggingsDropChance(float chance);

	void setChestplateDropChance(float chance);

	void setHelmetDropChance(float chance);

	void setOffHandChance(float chance);

	void setMainHandDropChance(float chance);
	
	void setBodyDropChance(float chance);
	
	void setSaddleDropChance(float chance);
	
	void setBoots(ItemStack item);

	void setLeggings(ItemStack item);

	void setChestplate(ItemStack item);

	void setHelmet(ItemStack item);

	void setMainHand(ItemStack item);

	void setOffHand(ItemStack item);
	
	void setBody(ItemStack item);

	void setSaddle(ItemStack item);
	
	boolean hasArmorDropChance();

	boolean hasArmor();

	boolean hasHandItemDropChance();

	boolean hasHandItem();

	float getBootsDropChance();

	float getChestplateDropChance();

	float getLeggingsDropChance();

	float getHelmetDropChance();

	ItemStack getBoots();
	
	ItemStack getLeggings();
	
	ItemStack getChestplate();
	
	ItemStack getHelmet();
	
	float getMainHandDropChance();
	
	float getOffHandDropChance();
	
	float getBodyDropChance();
	
	float getSaddleDropChance();
	
	ItemStack getMainHand();
	
	ItemStack getOffHand();
	
	ItemStack getBody();
	
	ItemStack getSaddle();

}
