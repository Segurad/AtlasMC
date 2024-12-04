package de.atlasmc.inventory.component;

import de.atlasmc.NamespacedKey;

public interface GliderComponent extends ItemComponent {
	
	public static final NamespacedKey COMPONENT_KEY = NamespacedKey.literal("minecraft:glider");
	
	GliderComponent clone();

}
