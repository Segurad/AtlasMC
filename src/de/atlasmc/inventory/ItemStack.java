package de.atlasmc.inventory;

import de.atlasmc.Material;
import de.atlasmc.inventory.meta.ItemMeta;
import de.atlasmc.util.nbt.NBT;

public class ItemStack {

	private int amount;
	private Material type;
	private ItemMeta meta;
	
	public ItemStack(Material mat, byte amount2) {
		// TODO Auto-generated constructor stub
	}

	public Material getType() {
		return type;
	}
	
	public int getAmount() {
		return amount;
	}
	
	public void setAmount(int amount) {
		if (amount > 127) throw new IllegalArgumentException("Amount can not be higher than 127");
		if (amount < -128) throw new IllegalArgumentException("Amount can not be lower than -128");
		this.amount = amount;
	}
	
	public ItemMeta getItemMeta() {
		if (!hasItemMeta()) meta = type.createItemMeta();
		return meta;
	}
	
	public boolean hasItemMeta() {
		return meta != null;
	}
	
	public boolean setItemMeta(ItemMeta meta) {
		if (type.isValidMeta(meta)) return false;
		this.meta = meta;
		return true;
	}

	/**
	 * 
	 * @return the NBT or null if the ItemStack has no ItemMeta
	 */
	public NBT getMetaAsNBT() {
		if (meta == null) return null;
		return meta.toNBT();
	}
	
}
