package de.atlasmc.node.entity;

import de.atlasmc.nbt.codec.NBTCodec;

public interface Vex extends Monster {
	
	public static final NBTCodec<Vex>
	NBT_HANDLER = NBTCodec
					.builder(Vex.class)
					.intField("life_ticks", Vex::getLifeTime, Vex::setLifeTime, -1)
					.boolField("IsAttacking", Vex::isAttacking, Vex::setAttacking, false) // non standard
					.build();
	
	boolean isAttacking();
	
	void setAttacking(boolean attacking);

	void setLifeTime(int ticks);
	
	/**
	 * Returns the time in ticks until this vex takes damage.<br>
	 * After it took damage the time will be set to 20.<br>
	 * Will be -1 if not counting
	 * @return ticks or -1
	 */
	int getLifeTime();
	
	@Override
	default NBTCodec<? extends Vex> getNBTCodec() {
		return NBT_HANDLER;
	}

}
