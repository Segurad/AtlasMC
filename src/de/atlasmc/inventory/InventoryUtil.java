package de.atlasmc.inventory;

import de.atlasmc.Material;

public class InventoryUtil {

	public static void setPlaceholder(Inventory inv, Material material) {
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

}
