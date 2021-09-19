package de.atlasmc.inventory;

import java.util.List;
import de.atlasmc.Material;
import de.atlasmc.chat.ChatUtil;
import de.atlasmc.chat.component.ChatComponent;
import de.atlasmc.enchantments.Enchantment;
import de.atlasmc.inventory.meta.ItemMeta;
import de.atlasmc.inventory.meta.lore.SimpleLore;

public class ItemUtil {

	public static ItemStack getItemStack(Material material, ChatComponent name) {
		return getItemStack(material, name, null, 1);
	}

	public static ItemStack getItemStack(Material material, ChatComponent name, List<ChatComponent> lore, int amount) {
		ItemStack item = new ItemStack(material, amount);
		if (name != null || lore != null) {
			ItemMeta meta = item.getItemMeta();
			if (name != null) meta.setDisplayName(name);
			if (lore != null) meta.setLore(new SimpleLore(lore));
			item.setItemMeta(meta);
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
		item.setItemMeta(meta);
		return item;
	}
}
