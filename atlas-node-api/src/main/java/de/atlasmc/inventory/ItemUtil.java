package de.atlasmc.inventory;

import java.util.List;

import de.atlasmc.Material;
import de.atlasmc.chat.Chat;
import de.atlasmc.chat.ChatUtil;
import de.atlasmc.enchantments.Enchantment;
import de.atlasmc.inventory.component.CustomNameComponent;
import de.atlasmc.inventory.component.EnchantmentComponent;
import de.atlasmc.inventory.component.LoreComponent;

public class ItemUtil {

	public static ItemStack getItemStack(Material material, Chat name) {
		return getItemStack(material, name, null, 1);
	}

	public static ItemStack getItemStack(Material material, Chat name, List<Chat> lore, int amount) {
		ItemStack item = new ItemStack(material, amount);
		if (name != null) {
			CustomNameComponent nameComp = item.getComponent(CustomNameComponent.COMPONENT_KEY);
			nameComp.setCustomName(name);
		}
		if (lore != null) {
			LoreComponent loreComp = item.getComponent(LoreComponent.COMPONENT_KEY);
			loreComp.setLore(lore);
		}
		return item;
	}

	public static ItemStack getPlaceholder(Material material, boolean glow) {
		ItemStack item = new ItemStack(material);
		CustomNameComponent nameComp = item.getComponent(CustomNameComponent.COMPONENT_KEY);
		nameComp.setCustomName(ChatUtil.ONE_SPACE);
		if (glow) {
			EnchantmentComponent enchComp = item.getComponent(EnchantmentComponent.COMPONENT_KEY);
			enchComp.addEnchant(Enchantment.LUCK, 1);
			enchComp.setShowTooltip(false);
		}
		return item;
	}
}
