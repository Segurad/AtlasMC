package de.atlasmc.inventory;

import java.util.List;
import de.atlasmc.Material;
import de.atlasmc.enchantments.Enchantment;
import de.atlasmc.inventory.meta.ItemMeta;

public class ItemUtil {

	public static ItemStack getItemStack(Material material, String name) {
		return getItemStack(material, name, null, 1);
	}

	public static ItemStack getItemStack(Material material, String name, List<String> lore, int amount) {
		ItemStack item = new ItemStack(material, amount);
		if (name != null || lore != null) {
			ItemMeta meta = item.getItemMeta();
			if (name != null) meta.setDisplayName(name);
			if (lore != null) meta.setLore(lore);
			item.setItemMeta(meta);
		}
		return item;
	}

	public static ItemStack getPlaceholder(Material material, boolean glow) {
		ItemStack item = new ItemStack(material);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(" ");
		if (glow) {
			meta.addEnchant(Enchantment.LUCK, 1);
			meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		}
		item.setItemMeta(meta);
		return item;
	}
}
