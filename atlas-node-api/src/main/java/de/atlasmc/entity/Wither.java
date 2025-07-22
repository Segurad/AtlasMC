package de.atlasmc.entity;

import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface Wither extends Monster {
	
	public static final NBTSerializationHandler<Wither>
	NBT_HANDLER = NBTSerializationHandler
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
	default NBTSerializationHandler<? extends Wither> getNBTHandler() {
		return NBT_HANDLER;
	}
	
}
