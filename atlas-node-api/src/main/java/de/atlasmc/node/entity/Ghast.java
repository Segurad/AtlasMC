package de.atlasmc.node.entity;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.util.annotation.NotNull;

public interface Ghast extends Monster {
	
	@NotNull
	public static final NBTCodec<Ghast>
	NBT_CODEC = NBTCodec
					.builder(Ghast.class)
					.include(Monster.NBT_CODEC)
					.byteField("ExplosionPower", Ghast::getExplosionPower, Ghast::setExplosionPower, (byte) 1)
					.boolField("Attacking", Ghast::isAttacking, Ghast::setAttacking, false) // non standard
					.build();
	
	boolean isAttacking();
	
	void setAttacking(boolean attacking);

	void setExplosionPower(int power);
	
	/**
	 * Returns the power this Ghast shoots fire balls with
	 * @return power
	 */
	int getExplosionPower();
	
	@Override
	default NBTCodec<? extends Ghast> getNBTCodec() {
		return NBT_CODEC;
	}

}
