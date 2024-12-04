package de.atlasmc.inventory.component;

import de.atlasmc.NamespacedKey;

public interface HideAdditionalTooltipComponent extends ItemComponent {
	
	public static final NamespacedKey COMPONENT_KEY = NamespacedKey.literal("minecraft:hide_additional_tooltip");
	
	HideAdditionalTooltipComponent clone();

}
