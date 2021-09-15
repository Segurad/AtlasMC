package de.atlasmc.inventory;

import java.util.Map;

import de.atlasmc.enchantments.Enchantment;

public interface EnchantingInventory extends Inventory {
	
	public int getRequiredLevelTop();
	
	public int getRequiredLevelMiddle();
	
	public int getRequiredLevelBottom();
	
	public void setRequiredLevelTop(int level);
	
	public void setRequiredLevelMiddle(int level);
	
	public void setRequiredLevelBottom(int level);
	
	public int getDisplaySeed();
	
	public void setDisplayedSeed(int seed);
	
	/**
	 * Returns the map with {@link Enchantment}s for the top slot (may be empty)
	 * @return a map with enchants and Levels
	 */
	public Map<Enchantment, Integer> getTopEnchants();
	
	/**
	 * Returns the map with {@link Enchantment}s for the middle slot (may be empty)
	 * @return a map with enchants and Levels
	 */
	public Map<Enchantment, Integer> getMiddleEnchants();
	
	/**
	 * Returns the map with {@link Enchantment}s for the bottom slot (may be empty)
	 * @return a map with enchants and Levels
	 */
	public Map<Enchantment, Integer> getBottomEnchants();
	
	public int getDisplayTopEnchantID();
	
	public int getDisplayMiddleEnchantID();
	
	public int getDisplayBottomEnchantID();
	
	public int getDisplayTopEnchantLevel();
	
	public int getDisplayMiddleEnchantLevel();
	
	public int getDisplayBottomEnchantLevel();
	
	/**
	 * Sets the displayed enchantment in the top slot by id or -1 to hide
	 * @param enchantID 
	 */
	public void setDisplayTopEnchantID(int enchantID);
	
	/**
	 * Sets the displayed enchantment in the middle slot by id or -1 to hide
	 * @param enchantID 
	 */
	public void setDisplayMiddleEnchantID(int enchantID);
	
	/**
	 * Sets the displayed enchantment in the bottom slot by id or -1 to hide
	 * @param enchantID 
	 */
	public void setDisplayBottomEnchantID(int enchantID);
	
	/**
	 * Sets the displayed enchantment level in the top slot or -1 if no enchantment is present.
	 * @param level of the enchantment
	 */
	public void setDisplayTopEnchantLevel(int level);
	
	/**
	 * Sets the displayed enchantment level in the middle slot or -1 if no enchantment is present.
	 * @param level of the enchantment
	 */
	public void setDisplayMiddleEnchantLevel(int level);
	
	/**
	 * Sets the displayed enchantment level in the bottom slot or -1 if no enchantment is present.
	 * @param level of the enchantment
	 */
	public void setDisplayBottomEnchantLevel(int level);
	
	public ItemStack getItem();
	
	public void setItem(ItemStack item);
	
	public ItemStack getSecondary();
	
	public void setSecondary(ItemStack secondary);
	
}
