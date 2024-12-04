package de.atlasmc.inventory.component;

import de.atlasmc.NamespacedKey;

public interface TooltipStyleComponent extends ItemComponent {
	
	public static final NamespacedKey COMPONENT_KEY = NamespacedKey.literal("minecraft:tooltip_style");
	
	NamespacedKey getStyle();
	
	void setStyle(NamespacedKey style);
	
	TooltipStyleComponent clone();

}
