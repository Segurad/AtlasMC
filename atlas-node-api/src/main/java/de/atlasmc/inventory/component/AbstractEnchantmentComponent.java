package de.atlasmc.inventory.component;

import de.atlasmc.enchantments.Enchantment;
import it.unimi.dsi.fastutil.objects.Object2IntMap;

public interface AbstractEnchantmentComponent extends ItemComponent {
	
	void addEnchant(Enchantment ench, int level);
	
	int getEnchantLevel(Enchantment ench);
	
	Object2IntMap<Enchantment> getStoredEnchants();
	
	boolean hasConflictingEnchant(Enchantment ench);
	
	boolean hasEnchants();
	
	boolean hasEnchant(Enchantment ench);
	
	void removeEnchant(Enchantment ench);
	
	AbstractEnchantmentComponent clone();

}
