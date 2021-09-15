package de.atlascore.inventory;

import java.util.HashMap;
import java.util.Map;

import de.atlasmc.enchantments.Enchantment;
import de.atlasmc.entity.Player;
import de.atlasmc.event.inventory.InventoryType;
import de.atlasmc.inventory.EnchantingInventory;
import de.atlasmc.inventory.InventoryHolder;
import de.atlasmc.inventory.ItemStack;

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
	
	private short dseed = (short) 0xFFAA0123;
	private short lvlTop = 10, lvlMid = 20, lvlBot = 30;
	private short dtopID = -1, dmidID = -1, dbotID = -1;
	private	short dtopLvl = -1, dmidLvl = -1, dbotLvl = -1;
	private Map<Enchantment, Integer> topEnch, midEnch, botEnch;
	
	public CoreEnchantingInventory(String title, InventoryHolder holder) {
		super(2, InventoryType.ENCHANTING, title, holder);
	}

	@Override
	public int getRequiredLevelTop() {
		return lvlTop;
	}

	@Override
	public int getRequiredLevelMiddle() {
		return lvlMid;
	}

	@Override
	public int getRequiredLevelBottom() {
		return lvlBot;
	}
	
	@Override
	public void setRequiredLevelTop(int level) {
		this.lvlTop = (short) level;
		updateProperty(PROPERTY_LVL_REQUIREMENT_TOP, level);
	}
	
	@Override
	public void setRequiredLevelMiddle(int level) {
		this.lvlMid = (short) level;
		updateProperty(PROPERTY_LVL_REQUIREMENT_MID, level);
	}
	
	@Override
	public void setRequiredLevelBottom(int level) {
		this.lvlBot = (short) level;
		updateProperty(PROPERTY_LVL_REQUIREMENT_BOT, level);
	}

	@Override
	public int getDisplaySeed() {
		return dseed;
	}
	
	public void setDisplayedSeed(int seed) {
		this.dseed = (short) seed;
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
		return dtopID;
	}

	@Override
	public int getDisplayMiddleEnchantID() {
		return dmidID;
	}

	@Override
	public int getDisplayBottomEnchantID() {
		return dbotID;
	}

	@Override
	public int getDisplayTopEnchantLevel() {
		return dtopLvl;
	}

	@Override
	public int getDisplayMiddleEnchantLevel() {
		return dmidLvl;
	}

	@Override
	public int getDisplayBottomEnchantLevel() {
		return dbotLvl;
	}

	@Override
	public void setDisplayTopEnchantID(int enchantID) {
		this.dtopID = (short) enchantID;
		updateProperty(PROPERTY_ENCH_ID_TOP, enchantID);
	}

	@Override
	public void setDisplayMiddleEnchantID(int enchantID) {
		this.dmidID = (short) enchantID;
		updateProperty(PROPERTY_ENCH_ID_MID, enchantID);
	}

	@Override
	public void setDisplayBottomEnchantID(int enchantID) {
		this.dbotID = (short) enchantID;
		updateProperty(PROPERTY_ENCH_ID_BOT, enchantID);
	}

	@Override
	public void setDisplayTopEnchantLevel(int level) {
		this.dtopLvl = (short) level;
		updateProperty(PROPERTY_ENCH_LVL_TOP, level);
	}

	@Override
	public void setDisplayMiddleEnchantLevel(int level) {
		this.dmidLvl = (short) level;
		updateProperty(PROPERTY_ENCH_LVL_MID, level);
	}

	@Override
	public void setDisplayBottomEnchantLevel(int level) {
		this.dbotLvl = (short) level;
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
	
	@Override
	public void updateProperties() {
		for (Player p : getViewers()) {
			updateProperties(p);
		}
	}
	
	@Override
	public void updateProperties(Player player) {
		updateProperty(PROPERTY_LVL_REQUIREMENT_TOP, lvlTop, player);
		updateProperty(PROPERTY_LVL_REQUIREMENT_MID, lvlMid, player);
		updateProperty(PROPERTY_LVL_REQUIREMENT_BOT, lvlBot, player);
		updateProperty(PROPERTY_ENCH_ID_TOP, dtopID, player);
		updateProperty(PROPERTY_ENCH_ID_MID, dmidID, player);
		updateProperty(PROPERTY_ENCH_ID_BOT, dbotID, player);
		updateProperty(PROPERTY_ENCH_LVL_TOP, dtopLvl, player);
		updateProperty(PROPERTY_ENCH_LVL_MID, dmidLvl, player);
		updateProperty(PROPERTY_ENCH_LVL_BOT, dbotLvl, player);
	}

}
