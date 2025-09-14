package de.atlasmc.node.entity;

import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface Ravager extends Raider {
	
	public static final NBTSerializationHandler<Ravager>
	NBT_HANDLER = NBTSerializationHandler
					.builder(Ravager.class)
					.include(Raider.NBT_HANDLER)
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
	default NBTSerializationHandler<? extends Ravager> getNBTHandler() {
		return NBT_HANDLER;
	}

}
