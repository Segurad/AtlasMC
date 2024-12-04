package de.atlasmc.inventory.component;

import de.atlasmc.NamespacedKey;

public interface StoredEnchantmentComponent extends AbstractEnchantmentComponent {
	
	public static final NamespacedKey COMPONENT_KEY = NamespacedKey.literal("minecraft:stored_enchantments");
	
	StoredEnchantmentComponent clone();

}
