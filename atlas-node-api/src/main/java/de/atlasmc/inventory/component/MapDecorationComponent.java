package de.atlasmc.inventory.component;

import java.util.Collection;

import de.atlasmc.NamespacedKey;
import de.atlasmc.map.MapIcon;

public interface MapDecorationComponent extends ItemComponent {
	
	public static final NamespacedKey COMPONENT_KEY = NamespacedKey.literal("minecraft:map_decorations");
	
	Collection<MapIcon> getDecorations();
	
	boolean hasDecoration();
	
	void addDecoration(MapIcon icon);
	
	void removeDecoration(MapIcon icon);
	
	MapIcon getDecoration(String name);
	
	void removeDecoration(String name);
	
	MapDecorationComponent clone();

}
