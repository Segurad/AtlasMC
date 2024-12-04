package de.atlasmc.inventory.component;

import de.atlasmc.NamespacedKey;

public interface IntangibleProjectileComponent extends ItemComponent {
	
	public static final NamespacedKey COMPONENT_KEY = NamespacedKey.literal("minecraft:intangible_projectile");
	
	IntangibleProjectileComponent clone();

}
