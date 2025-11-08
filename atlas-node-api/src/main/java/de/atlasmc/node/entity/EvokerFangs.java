package de.atlasmc.node.entity;

import java.util.UUID;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.nbt.codec.NBTCodecs;

public interface EvokerFangs extends Entity {
	
	public static final NBTCodec<EvokerFangs>
	NBT_HANDLER = NBTCodec
					.builder(EvokerFangs.class)
					.include(Entity.NBT_CODEC)
					.codec("Owner", EvokerFangs::getCasterUUID, EvokerFangs::setCasterUUID, NBTCodecs.UUID_CODEC)
					.intField("Warmup", EvokerFangs::getWarmup, EvokerFangs::setWarmup)
					.build();
	
	void setCaster(Entity caster);
	
	Entity getCaster();

	UUID getCasterUUID();
	
	void setCasterUUID(UUID uuid);
	
	int getWarmup();
	
	void setWarmup(int warmup);
	
	@Override
	default NBTCodec<? extends EvokerFangs> getNBTCodec() {
		return NBT_HANDLER;
	}
	
}
