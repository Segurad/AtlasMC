package de.atlasmc.inventory.component;

import de.atlasmc.NamespacedKey;

public interface ContainerLootComponent extends ItemComponent {
	
	public static final NamespacedKey COMPONENT_KEY = NamespacedKey.literal("minecraft:container_loot");
	
	NamespacedKey getLootTableKey();
	
	void setLootTableKey(NamespacedKey key);
	
	long getSeed();
	
	void setSeed(long seed);
	
	ContainerLootComponent clone();

}
