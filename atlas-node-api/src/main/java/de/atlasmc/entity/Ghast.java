package de.atlasmc.entity;

import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface Ghast extends Monster {
	
	public static final NBTSerializationHandler<Ghast>
	NBT_HANDLER = NBTSerializationHandler
					.builder(Ghast.class)
					.include(Ghast.NBT_HANDLER)
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
	default NBTSerializationHandler<? extends Ghast> getNBTHandler() {
		return NBT_HANDLER;
	}

}
