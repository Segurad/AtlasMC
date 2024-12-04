package de.atlasmc.inventory.component;

import de.atlasmc.NamespacedKey;

public interface UnbreakableComponent extends AbstractTooltipComponent {
	
	public static final NamespacedKey COMPONENT_KEY = NamespacedKey.literal("minecraft:unbreakable");

	UnbreakableComponent clone();
	
}
