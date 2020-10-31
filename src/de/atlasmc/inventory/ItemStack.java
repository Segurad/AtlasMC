package de.atlasmc.inventory;

import de.atlasmc.Material;
import de.atlasmc.inventory.meta.ItemMeta;

public class ItemStack {

	private int amount;
	private Material type;
	private ItemMeta meta;
	
	public Material getType() {
		return type;
	}
	
	public int getAmount() {
		return amount;
	}
	
	public void setAmount(int amount) {
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
	
}
