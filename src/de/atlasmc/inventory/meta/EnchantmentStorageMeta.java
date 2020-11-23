package de.atlasmc.inventory.meta;

import java.util.Map;

import de.atlasmc.enchantments.Enchantment;

public interface EnchantmentStorageMeta extends ItemMeta {
	
	public boolean addStoredEnchant(Enchantment ench, int level);
	public EnchantmentStorageMeta clone();
	public int getStoredEnchantLevel(Enchantment ench);
	public Map<Enchantment, Integer>getStoredEnchants();
	public boolean hasConflictingStoredEnchant(Enchantment ench);
	public boolean hasStoredEnchants();
	public boolean removeStoredEnchant(Enchantment ench);

}
