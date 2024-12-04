package de.atlasmc.inventory.component;

import de.atlasmc.NamespacedKey;

public interface MaxStackSizeComponent {

	public static final NamespacedKey COMPONENT_KEY = NamespacedKey.literal("minecraft:max_stack_size");
	
	int getMaxStackSize();
	
	void setMaxStackSize(int stackSize);
	
	MaxStackSizeComponent clone();
	
}
