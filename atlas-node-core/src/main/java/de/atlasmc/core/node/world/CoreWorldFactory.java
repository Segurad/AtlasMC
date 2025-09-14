package de.atlasmc.core.node.world;

import de.atlasmc.node.world.World;
import de.atlasmc.node.world.WorldBuilder;
import de.atlasmc.node.world.WorldFactory;
import de.atlasmc.registry.RegistryValue;

@RegistryValue(registry="atlas:factory/world_factory", key="atlas-core:world", isDefault = true)
public class CoreWorldFactory implements WorldFactory {

	@Override
	public World createWorld(WorldBuilder builder) {
		return new CoreWorld(builder);
	}

}
