package de.atlasmc.node.entity;

import de.atlasmc.nbt.codec.NBTCodec;

public interface Raider extends Monster {
	
	public static final NBTCodec<Raider>
	NBT_HANDLER = NBTCodec
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
	default NBTCodec<? extends Raider> getNBTCodec() {
		return NBT_HANDLER;
	}

}
