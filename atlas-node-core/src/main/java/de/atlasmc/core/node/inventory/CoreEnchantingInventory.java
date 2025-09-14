package de.atlasmc.core.node.inventory;

import java.util.HashMap;
import java.util.Map;

import de.atlasmc.chat.Chat;
import de.atlasmc.node.enchantments.Enchantment;
import de.atlasmc.node.inventory.EnchantingInventory;
import de.atlasmc.node.inventory.InventoryHolder;
import de.atlasmc.node.inventory.InventoryType;
import de.atlasmc.node.inventory.ItemStack;

public class CoreEnchantingInventory extends CoreInventory implements EnchantingInventory {

	protected static final byte
	PROPERTY_LVL_REQUIREMENT_TOP = 0,
	PROPERTY_LVL_REQUIREMENT_MID = 1,
	PROPERTY_LVL_REQUIREMENT_BOT = 2,
	PROPERTY_ENCH_SEED = 3,
	PROPERTY_ENCH_ID_TOP = 4,
	PROPERTY_ENCH_ID_MID = 5,
	PROPERTY_ENCH_ID_BOT = 6,
	PROPERTY_ENCH_LVL_TOP = 7,
	PROPERTY_ENCH_LVL_MID = 8,
	PROPERTY_ENCH_LVL_BOT = 9;
	
	private Map<Enchantment, Integer> topEnch;
	private Map<Enchantment, Integer> midEnch;
	private Map<Enchantment, Integer> botEnch;
	
	public CoreEnchantingInventory(Chat title, InventoryHolder holder) {
		super(2, 1, InventoryType.ENCHANTING, title, holder);
		properties[PROPERTY_LVL_REQUIREMENT_TOP] = 10;
		properties[PROPERTY_LVL_REQUIREMENT_MID] = 20;
		properties[PROPERTY_LVL_REQUIREMENT_BOT] = 30;
		properties[PROPERTY_ENCH_SEED] = 0xCAFFEE;
		properties[PROPERTY_ENCH_ID_TOP] = -1;
		properties[PROPERTY_ENCH_ID_MID] = -1;
		properties[PROPERTY_ENCH_ID_BOT] = -1;
		properties[PROPERTY_ENCH_LVL_TOP] = -1;
		properties[PROPERTY_ENCH_LVL_MID] = -1;
		properties[PROPERTY_ENCH_LVL_BOT] = -1;
	}
	
	@Override
	protected int getPropertyCount() {
		return 10;
	}

	@Override
	public int getRequiredLevelTop() {
		return properties[PROPERTY_LVL_REQUIREMENT_TOP];
	}

	@Override
	public int getRequiredLevelMiddle() {
		return properties[PROPERTY_LVL_REQUIREMENT_MID];
	}

	@Override
	public int getRequiredLevelBottom() {
		return properties[PROPERTY_LVL_REQUIREMENT_BOT];
	}
	
	@Override
	public void setRequiredLevelTop(int level) {
		updateProperty(PROPERTY_LVL_REQUIREMENT_TOP, level);
	}
	
	@Override
	public void setRequiredLevelMiddle(int level) {
		updateProperty(PROPERTY_LVL_REQUIREMENT_MID, level);
	}
	
	@Override
	public void setRequiredLevelBottom(int level) {
		updateProperty(PROPERTY_LVL_REQUIREMENT_BOT, level);
	}

	@Override
	public int getDisplaySeed() {
		return properties[PROPERTY_ENCH_SEED];
	}
	
	public void setDisplayedSeed(int seed) {
		updateProperty(PROPERTY_ENCH_SEED, seed);
	}

	@Override
	public Map<Enchantment, Integer> getTopEnchants() {
		if (topEnch == null) topEnch = new HashMap<>();
		return topEnch;
	}

	@Override
	public Map<Enchantment, Integer> getMiddleEnchants() {
		if (midEnch == null) midEnch = new HashMap<>();
		return midEnch;
	}

	@Override
	public Map<Enchantment, Integer> getBottomEnchants() {
		if (botEnch == null) botEnch = new HashMap<>();
		return botEnch;
	}

	@Override
	public int getDisplayTopEnchantID() {
		return properties[PROPERTY_ENCH_ID_TOP];
	}

	@Override
	public int getDisplayMiddleEnchantID() {
		return properties[PROPERTY_ENCH_ID_MID];
	}

	@Override
	public int getDisplayBottomEnchantID() {
		return properties[PROPERTY_ENCH_ID_BOT];
	}

	@Override
	public int getDisplayTopEnchantLevel() {
		return properties[PROPERTY_ENCH_LVL_TOP];
	}

	@Override
	public int getDisplayMiddleEnchantLevel() {
		return properties[PROPERTY_ENCH_LVL_MID];
	}

	@Override
	public int getDisplayBottomEnchantLevel() {
		return properties[PROPERTY_ENCH_LVL_BOT];
	}

	@Override
	public void setDisplayTopEnchantID(int enchantID) {
		updateProperty(PROPERTY_ENCH_ID_TOP, enchantID);
	}

	@Override
	public void setDisplayMiddleEnchantID(int enchantID) {
		updateProperty(PROPERTY_ENCH_ID_MID, enchantID);
	}

	@Override
	public void setDisplayBottomEnchantID(int enchantID) {
		updateProperty(PROPERTY_ENCH_ID_BOT, enchantID);
	}

	@Override
	public void setDisplayTopEnchantLevel(int level) {
		updateProperty(PROPERTY_ENCH_LVL_TOP, level);
	}

	@Override
	public void setDisplayMiddleEnchantLevel(int level) {
		updateProperty(PROPERTY_ENCH_LVL_MID, level);
	}

	@Override
	public void setDisplayBottomEnchantLevel(int level) {
		updateProperty(PROPERTY_ENCH_LVL_BOT, level);
	}

	@Override
	public ItemStack getItem() {
		return getItem(0);
	}

	@Override
	public void setItem(ItemStack item) {
		setItem(0, item);
	}

	@Override
	public ItemStack getSecondary() {
		return getItem(1);
	}

	@Override
	public void setSecondary(ItemStack secondary) {
		setItem(1, secondary);
	}

}
