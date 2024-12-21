package de.atlasmc.inventory;

public interface EntityEquipment {

	void setBootsDropChance(float chance);

	void setLeggingsDropChance(float chance);

	void setChestplateDropChance(float chance);

	void setHelmetDropChance(float chance);

	void setBoots(ItemStack item);

	void setLeggings(ItemStack item);

	void setChestplate(ItemStack item);

	void setHelmet(ItemStack item);

	void setOffHandChance(float chance);

	void setMainHandDropChance(float chance);

	void setMainHand(ItemStack item);

	void setOffHand(ItemStack item);

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
	
	ItemStack getMainHand();
	
	ItemStack getOffHand();

}
