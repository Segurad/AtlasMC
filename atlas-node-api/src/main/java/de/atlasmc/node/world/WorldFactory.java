package de.atlasmc.node.world;

import de.atlasmc.registry.RegistryHolder;
import de.atlasmc.util.annotation.NotNull;

@RegistryHolder(key="atlas:factory/world_factory")
public interface WorldFactory {

	@NotNull
	World createWorld(WorldBuilder builder);

}
