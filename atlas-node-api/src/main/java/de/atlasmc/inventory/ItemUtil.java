package de.atlasmc.inventory;

import java.util.List;

import de.atlasmc.NamespacedKey;
import de.atlasmc.chat.Chat;
import de.atlasmc.chat.ChatUtil;
import de.atlasmc.enchantments.Enchantment;
import de.atlasmc.inventory.component.CustomNameComponent;
import de.atlasmc.inventory.component.EnchantmentComponent;
import de.atlasmc.inventory.component.LoreComponent;

public class ItemUtil {

	public static ItemStack getItemStack(NamespacedKey type, Chat name) {
		return getItemStack(ItemType.get(type), name, null, 1);
	}
	
	public static ItemStack getItemStack(ItemType type, Chat name) {
		return getItemStack(type, name, null, 1);
	}

	public static ItemStack getItemStack(ItemType type, Chat name, List<Chat> lore, int amount) {
		ItemStack item = new ItemStack(type, amount);
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

	public static ItemStack getPlaceholder(ItemType type, boolean glow) {
		ItemStack item = new ItemStack(type);
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
