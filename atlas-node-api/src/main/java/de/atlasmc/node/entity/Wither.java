package de.atlasmc.node.entity;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.util.annotation.NotNull;

public interface Wither extends Monster {
	
	@NotNull
	public static final NBTCodec<Wither>
	NBT_CODEC = NBTCodec
					.builder(Wither.class)
					.include(Monster.NBT_CODEC)
					.intField("Invul", Wither::getInvulnerableTime, Wither::setInvulnerableTime, 0)
					.build();
	
	Entity getCenterHeadTarget();
	
	Entity getLeftHeadTarget();
	
	Entity getRightHeadTarget();
	
	int getInvulnerableTime();
	
	void setInvulnerableTime(int time);
	
	void setCenterHeadTarget(Entity entity);
	
	void setLeftHeadTarget(Entity entity);
	
	void setRightHeadTarget(Entity entity);

	@Override
	default NBTCodec<? extends Wither> getNBTCodec() {
		return NBT_CODEC;
	}
	
}
