package de.atlasmc.node.inventory;

import java.util.Map;

import de.atlasmc.node.enchantments.Enchantment;

public interface EnchantingInventory extends Inventory {
	
	int getRequiredLevelTop();
	
	int getRequiredLevelMiddle();
	
	int getRequiredLevelBottom();
	
	void setRequiredLevelTop(int level);
	
	void setRequiredLevelMiddle(int level);
	
	void setRequiredLevelBottom(int level);
	
	int getDisplaySeed();
	
	void setDisplayedSeed(int seed);
	
	/**
	 * Returns the map with {@link Enchantment}s for the top slot (may be empty)
	 * @return a map with enchants and Levels
	 */
	Map<Enchantment, Integer> getTopEnchants();
	
	/**
	 * Returns the map with {@link Enchantment}s for the middle slot (may be empty)
	 * @return a map with enchants and Levels
	 */
	Map<Enchantment, Integer> getMiddleEnchants();
	
	/**
	 * Returns the map with {@link Enchantment}s for the bottom slot (may be empty)
	 * @return a map with enchants and Levels
	 */
	Map<Enchantment, Integer> getBottomEnchants();
	
	int getDisplayTopEnchantID();
	
	int getDisplayMiddleEnchantID();
	
	int getDisplayBottomEnchantID();
	
	int getDisplayTopEnchantLevel();
	
	int getDisplayMiddleEnchantLevel();
	
	int getDisplayBottomEnchantLevel();
	
	/**
	 * Sets the displayed enchantment in the top slot by id or -1 to hide
	 * @param enchantID 
	 */
	void setDisplayTopEnchantID(int enchantID);
	
	/**
	 * Sets the displayed enchantment in the middle slot by id or -1 to hide
	 * @param enchantID 
	 */
	void setDisplayMiddleEnchantID(int enchantID);
	
	/**
	 * Sets the displayed enchantment in the bottom slot by id or -1 to hide
	 * @param enchantID 
	 */
	void setDisplayBottomEnchantID(int enchantID);
	
	/**
	 * Sets the displayed enchantment level in the top slot or -1 if no enchantment is present.
	 * @param level of the enchantment
	 */
	void setDisplayTopEnchantLevel(int level);
	
	/**
	 * Sets the displayed enchantment level in the middle slot or -1 if no enchantment is present.
	 * @param level of the enchantment
	 */
	void setDisplayMiddleEnchantLevel(int level);
	
	/**
	 * Sets the displayed enchantment level in the bottom slot or -1 if no enchantment is present.
	 * @param level of the enchantment
	 */
	void setDisplayBottomEnchantLevel(int level);
	
	ItemStack getItem();
	
	void setItem(ItemStack item);
	
	ItemStack getSecondary();
	
	void setSecondary(ItemStack secondary);
	
}
