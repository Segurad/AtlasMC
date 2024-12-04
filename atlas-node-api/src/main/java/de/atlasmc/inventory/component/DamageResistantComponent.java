package de.atlasmc.inventory.component;

import de.atlasmc.NamespacedKey;

public interface DamageResistantComponent extends ItemComponent {
	
	public static final NamespacedKey COMPONENT_KEY = NamespacedKey.literal("minecraft:damage_resistant");
	
	// TODO damage resistant component
	
	DamageResistantComponent clone();

}
