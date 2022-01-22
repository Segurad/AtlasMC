package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.Minecart;
import de.atlasmc.world.World;

public class CoreMinecart extends CoreAbstractMinecart implements Minecart {

	public CoreMinecart(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
	}

}
