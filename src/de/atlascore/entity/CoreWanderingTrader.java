package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.WanderingTrader;
import de.atlasmc.world.World;

public class CoreWanderingTrader extends CoreAbstractVillager implements WanderingTrader {

	public CoreWanderingTrader(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
	}

}
