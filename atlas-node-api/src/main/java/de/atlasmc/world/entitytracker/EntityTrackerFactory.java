package de.atlasmc.world;

import de.atlasmc.registry.RegistryHolder;

@RegistryHolder(key = "atlas:factory/entity_tracker")
public interface EntityTrackerFactory {
	
	EntityTracker createEntityTracker(World world);

}
