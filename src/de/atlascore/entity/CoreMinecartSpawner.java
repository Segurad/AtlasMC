package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.MinecartSpawner;
import de.atlasmc.world.World;

public class CoreMinecartSpawner extends CoreAbstractMinecart implements MinecartSpawner {

	public CoreMinecartSpawner(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
	}

}
