package de.atlasmc.entity;

import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface Raider extends Monster {
	
	public static final NBTSerializationHandler<Raider>
	NBT_HANDLER = NBTSerializationHandler
					.builder(Raider.class)
					.include(Monster.NBT_HANDLER)
					.boolField("IsCelebrating", Raider::isCelebrating, Raider::setCelebrating, false) // non standard
					// CanJoinRaid
					// PatrolLeader
					// Patrolling
					// patrol_target
					// RaidId
					// Wave
					.build();
	
	boolean isCelebrating();
	
	void setCelebrating(boolean celebrating);
	
	@Override
	default NBTSerializationHandler<? extends Raider> getNBTHandler() {
		return NBT_HANDLER;
	}

}
