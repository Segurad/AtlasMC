package de.atlasmc.inventory.component;

import de.atlasmc.NamespacedKey;
import de.atlasmc.inventory.ItemStack;

public interface UseRemainderComponent extends ItemComponent {
	
	public static final NamespacedKey COMPONENT_KEY = NamespacedKey.literal("minecraft:use_remainder");
	
	ItemStack getItem();
	
	void setItem(ItemStack item);
	
	UseRemainderComponent clone();

}
