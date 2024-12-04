package de.atlasmc.inventory.component;

import de.atlasmc.NamespacedKey;

public interface OminousBottleAmplifierComponent extends ItemComponent {
	
	public static final NamespacedKey COMPONENT_KEY = NamespacedKey.literal("minecraft:ominous_bottle_amplifier");
	
	int getAmplifier();
	
	void setAmplifier(int amplifier);
	
	OminousBottleAmplifierComponent clone();

}
