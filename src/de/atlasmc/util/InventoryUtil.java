package de.atlasmc.util;

public class InventoryUtil {

	/*public static void setPlaceholder(Inventory inv, Material material) {
		setPlaceholder(inv, material, false);
	}

	public static void setPlaceholder(Inventory inv, Material material, boolean glow) {
		setPlaceholder(inv, ItemUtil.getPlaceholder(material, glow));
	}

	public static void setPlaceholder(Inventory inv, ItemStack item) {
		for (int i = 0; i < inv.getSize(); i++) {
			inv.setItem(i, item);
		}
	}

	public static Inventory copy(Inventory inv) {
		return copy(inv, null);
	}

	public static Inventory copy(Inventory inv, String name) {
		Inventory new_inv = null;
		if (name != null) {
			if (inv.getType() == InventoryType.CHEST) {
				new_inv = Bukkit.createInventory(inv.getHolder(), inv.getSize(), name);
			} else {
				new_inv = Bukkit.createInventory(inv.getHolder(), inv.getType(), name);
			}
		} else if (inv.getType() == InventoryType.CHEST) {
			new_inv = Bukkit.createInventory(inv.getHolder(), inv.getSize());
		} else {
			new_inv = Bukkit.createInventory(inv.getHolder(), inv.getType());
		}
		if (new_inv != null)
			new_inv.setContents(inv.getContents().clone());
		return new_inv;
	}

	public static void setPlaceholder(Inventory inv, Material material, boolean glow, int line) {
		setPlaceholder(inv, ItemUtil.getPlaceholder(material, glow), line);
	}

	public static void setPlaceholder(Inventory inv, ItemStack item, int line) {
		int end = line * 9;
		int start = (line - 1) * 9;
		for (int i = start; i < end; i++) {
			inv.setItem(i, item);
		}
	}
	
	public static void removeMaterial(Inventory inv, Material material, int amount) {
		for (int i = 0; i <= 36; i++) {
			ItemStack item = inv.getItem(i);
			if (item != null) {
				if (item.getType() == material) {
					if (amount > 0) {
						if (item.getAmount() - amount <= 0) {
							amount -= item.getAmount();
							inv.setItem(i, new ItemStack(Material.AIR));
						} else {
							int item_amount = item.getAmount();
							item.setAmount(item.getAmount() - amount);
							amount -= item_amount;
						}
					} else break;
				}
			}
		}
	}
	
	public static int countMaterial(Inventory inv, Material material) {
		int money_value = 0;
		for (int i = 0; i <= 36; i++) {
			ItemStack item = inv.getItem(i);
			if (item != null) {
				if (item.getType() == material) {
					money_value = money_value + item.getAmount();
				}
			}
		}
		return money_value;
	}*/
}
