package de.atlasmc.node.entity;

import de.atlasmc.util.nbt.codec.NBTCodec;

public interface Wither extends Monster {
	
	public static final NBTCodec<Wither>
	NBT_HANDLER = NBTCodec
					.builder(Wither.class)
					.include(Monster.NBT_HANDLER)
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
		return NBT_HANDLER;
	}
	
}
