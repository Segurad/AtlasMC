package de.atlasmc.inventory;

import java.util.List;

import de.atlasmc.Material;
import de.atlasmc.chat.Chat;
import de.atlasmc.chat.ChatUtil;
import de.atlasmc.enchantments.Enchantment;
import de.atlasmc.inventory.meta.ItemMeta;

public class ItemUtil {

	public static ItemStack getItemStack(Material material, Chat name) {
		return getItemStack(material, name, null, 1);
	}

	public static ItemStack getItemStack(Material material, Chat name, List<Chat> lore, int amount) {
		ItemStack item = new ItemStack(material, amount);
		if (name != null || lore != null) {
			ItemMeta meta = item.getItemMeta();
			if (name != null) 
				meta.setDisplayName(name);
			if (lore != null)
				meta.setLore(lore);
		}
		return item;
	}

	public static ItemStack getPlaceholder(Material material, boolean glow) {
		ItemStack item = new ItemStack(material);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatUtil.ONE_SPACE);
		if (glow) {
			meta.addEnchant(Enchantment.LUCK, 1);
			meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		}
		return item;
	}
}
