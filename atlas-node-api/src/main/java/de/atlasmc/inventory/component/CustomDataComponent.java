package de.atlasmc.inventory.component;

import de.atlasmc.NamespacedKey;

public interface CustomDataComponent extends ItemComponent {
	
	public static final NamespacedKey COMPONENT_KEY = NamespacedKey.literal("minecraft:custom_data");
	
	CustomDataComponent clone();
	
	//TODO custom data

}
