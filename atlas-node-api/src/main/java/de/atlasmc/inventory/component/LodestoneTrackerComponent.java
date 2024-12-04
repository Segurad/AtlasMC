package de.atlasmc.inventory.component;

import de.atlasmc.NamespacedKey;
import de.atlasmc.SimpleLocation;

public interface LodestoneTrackerComponent extends ItemComponent {
	
	public static final NamespacedKey COMPONENT_KEY = NamespacedKey.literal("minecraft:lodestone_tracker");
	
	SimpleLocation getLocation();
	
	void setLocation(SimpleLocation location);
	
	NamespacedKey getDimension();
	
	void setDimension(NamespacedKey dimension);
	
	LodestoneTrackerComponent clone();

}
