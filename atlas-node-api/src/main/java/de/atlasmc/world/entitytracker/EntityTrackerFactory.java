package de.atlasmc.world.entitytracker;

import de.atlasmc.registry.RegistryHolder;
import de.atlasmc.world.World;

@RegistryHolder(key = "atlas:factory/entity_tracker")
public interface EntityTrackerFactory {
	
	EntityTracker createEntityTracker(World world);

}
