package de.atlasmc.node.world.entitytracker;

import de.atlasmc.node.world.World;
import de.atlasmc.registry.RegistryHolder;

@RegistryHolder(key = "atlas:factory/entity_tracker")
public interface EntityTrackerFactory {
	
	EntityTracker createEntityTracker(World world);

}
