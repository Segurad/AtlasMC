package de.atlasmc.factory;

import de.atlasmc.registry.RegistryHolder;
import de.atlasmc.world.World;
import de.atlasmc.world.WorldBuilder;

@RegistryHolder(key="atlas:factory/world_factory")
public interface WorldFactory {

	World createWorld(WorldBuilder builder);

}
