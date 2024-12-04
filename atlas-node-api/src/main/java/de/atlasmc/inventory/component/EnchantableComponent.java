package de.atlasmc.inventory.component;

import de.atlasmc.NamespacedKey;

public interface EnchantableComponent extends ItemComponent {
	
	public static final NamespacedKey COMPONENT_KEY = NamespacedKey.literal("minecraft:enchantable");
	
	int getValue();
	
	void setValue(int value);
	
	EnchantableComponent clone();

}
