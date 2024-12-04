package de.atlasmc.inventory.component;

import de.atlasmc.Color;
import de.atlasmc.NamespacedKey;

public interface MapColorComponent extends ItemComponent {
	
	public static final NamespacedKey COMPONENT_KEY = NamespacedKey.literal("minecraft:map_color");
	
	Color getColor();
	
	void setColor(Color color);
	
	MapColorComponent clone();

}
