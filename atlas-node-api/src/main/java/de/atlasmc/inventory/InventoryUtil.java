package de.atlasmc.inventory;

import java.util.Arrays;

import de.atlasmc.Material;

public class InventoryUtil {

	public static void setPlaceholder(Inventory inv, Material material) {
		setPlaceholder(inv, material, false);
	}

	public static void setPlaceholder(Inventory inv, Material material, boolean glow) {
		setPlaceholder(inv, ItemUtil.getPlaceholder(material, glow));
	}

	public static void setPlaceholder(Inventory inv, ItemStack item) {
		if (inv == null)
			throw new IllegalArgumentException("Inventory can not be null!");
		if (item == null)
			throw new IllegalArgumentException("Item can not be null!");
		ItemStack[] contents = new ItemStack[inv.getSize()];
		Arrays.fill(contents, item);
		inv.setContentsUnsafe(contents);
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
