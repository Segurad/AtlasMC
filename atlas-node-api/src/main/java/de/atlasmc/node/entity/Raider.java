package de.atlasmc.node.entity;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.util.annotation.NotNull;

public interface Raider extends Monster {
	
	@NotNull
	public static final NBTCodec<Raider>
	NBT_CODEC = NBTCodec
					.builder(Raider.class)
					.include(Monster.NBT_CODEC)
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
		return NBT_CODEC;
	}

}
