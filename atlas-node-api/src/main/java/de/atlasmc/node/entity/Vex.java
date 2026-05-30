package de.atlasmc.node.entity;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.util.annotation.NotNull;

public interface Vex extends Monster {
	
	@NotNull
	public static final NBTCodec<Vex>
	NBT_CODEC = NBTCodec
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
		return NBT_CODEC;
	}

}
