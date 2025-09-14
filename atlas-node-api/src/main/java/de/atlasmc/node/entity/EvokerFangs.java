package de.atlasmc.node.entity;

import java.util.UUID;

import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface EvokerFangs extends Entity {
	
	public static final NBTSerializationHandler<EvokerFangs>
	NBT_HANDLER = NBTSerializationHandler
					.builder(EvokerFangs.class)
					.include(Entity.NBT_HANDLER)
					.uuid("Owner", EvokerFangs::getCasterUUID, EvokerFangs::setCasterUUID)
					.intField("Warmup", EvokerFangs::getWarmup, EvokerFangs::setWarmup)
					.build();
	
	void setCaster(Entity caster);
	
	Entity getCaster();

	UUID getCasterUUID();
	
	void setCasterUUID(UUID uuid);
	
	int getWarmup();
	
	void setWarmup(int warmup);
	
	@Override
	default NBTSerializationHandler<? extends EvokerFangs> getNBTHandler() {
		return NBT_HANDLER;
	}
	
}
