package de.atlasmc.node.entity;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.util.annotation.NotNull;

public interface Ravager extends Raider {
	
	@NotNull
	public static final NBTCodec<Ravager>
	NBT_CODEC = NBTCodec
					.builder(Ravager.class)
					.include(Raider.NBT_CODEC)
					.intField("AttackTick", Ravager::getAttackCooldown, Ravager::setAttackCooldown, 0)
					.intField("RoarTick", Ravager::getRoarTime, Ravager::setRoarTime, 0)
					.intField("StunTick", Ravager::getStunTime, Ravager::setStunTime, 0)
					.build();

	void setAttackCooldown(int ticks);
	
	int getAttackCooldown();
	
	void setRoarTime(int ticks);
	
	int getRoarTime();
	
	void setStunTime(int ticks);
	
	int getStunTime();
	
	@Override
	default NBTCodec<? extends Ravager> getNBTCodec() {
		return NBT_CODEC;
	}

}
