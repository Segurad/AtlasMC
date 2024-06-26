package de.atlasmc.world;

import de.atlasmc.registry.RegistryHolder;

@RegistryHolder(key="atlas:factory/world_factory")
public interface WorldFactory {

	World createWorld(WorldBuilder builder);

}
