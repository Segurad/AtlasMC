package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.Entity;
import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.EvokerFangs;
import de.atlasmc.world.World;

public class CoreEvokerFangs extends CoreEntity implements EvokerFangs {

//	protected static final NBTFieldContainer NBT_FIELDS; TODO implement EvokerFangs
//	
//	protected static final String
//	NBT_OWNER = "Owner",
//	NBT_WARMUP = "Warmup";
	
	private Entity caster;
	
	public CoreEvokerFangs(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
	}

	@Override
	public void setCaster(Entity caster) {
		this.caster = caster;
	}

	@Override
	public Entity getCaster() {
		return caster;
	}

}
