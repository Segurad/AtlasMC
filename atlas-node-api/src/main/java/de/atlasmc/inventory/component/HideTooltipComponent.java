package de.atlasmc.inventory.component;

import de.atlasmc.NamespacedKey;

public interface HideTooltipComponent extends ItemComponent {
	
	public static final NamespacedKey COMPONENT_KEY = NamespacedKey.literal("minecraft:hide_tooltip");
	
	HideTooltipComponent clone();

}
