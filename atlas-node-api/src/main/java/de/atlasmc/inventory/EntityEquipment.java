package de.atlasmc.inventory;

public interface EntityEquipment {

	public void setBootsDropChance(float chance);

	public void setLeggingsDropChance(float chance);

	public void setChestplateDropChance(float chance);

	public void setHelmetDropChance(float chance);

	public void setBoots(ItemStack item);

	public void setLeggings(ItemStack item);

	public void setChestplate(ItemStack item);

	public void setHelmet(ItemStack item);

	public void setOffHandChance(float chance);

	public void setMainHandDropChance(float chance);

	public void setMainHand(ItemStack item);

	public void setOffHand(ItemStack item);

	public boolean hasArmorDropChance();

	public boolean hasArmor();

	public boolean hasHandItemDropChance();

	public boolean hasHandItem();

	public float getBootsDropChance();

	public float getChestplateDropChance();

	public float getLeggingsDropChance();

	public float getHelmetDropChance();

	public ItemStack getBoots();
	
	public ItemStack getLeggings();
	
	public ItemStack getChestplate();
	
	public ItemStack getHelmet();
	
	public float getMainHandDropChance();
	
	public float getOffHandDropChance();
	
	public ItemStack getMainHand();
	
	public ItemStack getOffHand();

}
