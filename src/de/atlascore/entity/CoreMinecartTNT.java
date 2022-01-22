package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.MinecartTNT;
import de.atlasmc.world.World;

public class CoreMinecartTNT extends CoreAbstractMinecart implements MinecartTNT {

	public CoreMinecartTNT(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
	}

}
