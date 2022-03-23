package de.atlasmc.inventory.meta;

import java.util.Map;

import de.atlasmc.enchantments.Enchantment;

public interface EnchantmentStorageMeta extends ItemMeta {
	
	public void addStoredEnchant(Enchantment ench, int level);
	
	public EnchantmentStorageMeta clone();
	
	public int getStoredEnchantLevel(Enchantment ench);
	
	public Map<Enchantment, Integer>getStoredEnchants();
	
	public boolean hasConflictingStoredEnchant(Enchantment ench);
	
	public boolean hasStoredEnchants();
	
	public boolean hasStoredEnchant(Enchantment ench);
	
	public void removeStoredEnchant(Enchantment ench);

}
