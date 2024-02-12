package de.atlascore.world;

import de.atlasmc.factory.WorldFactory;
import de.atlasmc.registry.RegistryValue;
import de.atlasmc.world.World;
import de.atlasmc.world.WorldBuilder;

@RegistryValue(registry="atlas:factory/world_factory", key="atlas-core:factory/world_factory", isDefault = true)
public class CoreWorldFactory implements WorldFactory {

	@Override
	public World createWorld(WorldBuilder builder) {
		return new CoreWorld(builder);
	}

}
