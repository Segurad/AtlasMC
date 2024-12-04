package de.atlasmc.inventory.component;

import de.atlasmc.DyeColor;
import de.atlasmc.NamespacedKey;

public interface BaseColorComponent extends ItemComponent {
	
	public static final NamespacedKey COMPONENT_KEY = NamespacedKey.literal("minecraft:base_color");
	
	BaseColorComponent clone();
	
	DyeColor getColor();
	
	void setColor(DyeColor color);

}
