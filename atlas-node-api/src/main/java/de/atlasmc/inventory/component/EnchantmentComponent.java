package de.atlasmc.inventory.component;

import de.atlasmc.NamespacedKey;

public interface EnchantmentComponent extends AbstractEnchantmentComponent {
	
	public static final NamespacedKey COMPONENT_KEY = NamespacedKey.literal("minecraft:enchantments");
	
	EnchantmentComponent clone();

}
