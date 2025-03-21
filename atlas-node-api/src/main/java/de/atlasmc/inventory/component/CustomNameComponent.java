package de.atlasmc.inventory.component;

import de.atlasmc.Nameable;
import de.atlasmc.NamespacedKey;

public interface CustomNameComponent extends ItemComponent, Nameable {
	
	public static NamespacedKey COMPONENT_KEY = NamespacedKey.literal("minecraft:custom_name");
	
	CustomNameComponent clone();

}
