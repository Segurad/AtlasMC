package de.atlascore.world;

import de.atlasmc.registry.RegistryValue;
import de.atlasmc.world.World;
import de.atlasmc.world.WorldBuilder;
import de.atlasmc.world.WorldFactory;

@RegistryValue(registry="atlas:factory/world_factory", key="atlas-core:world", isDefault = true)
public class CoreWorldFactory implements WorldFactory {

	@Override
	public World createWorld(WorldBuilder builder) {
		return new CoreWorld(builder);
	}

}
