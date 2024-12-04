package de.atlasmc.inventory.component;

import de.atlasmc.NamespacedKey;

public interface MapIDComponent extends ItemComponent {
	
	public static final NamespacedKey COMPONENT_KEY = NamespacedKey.literal("minecraft:map_id");
	
	int getMapID();
	
	void setMapID(int id);
	
	MapIDComponent clone();

}
