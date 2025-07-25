package de.atlasmc.entity;

import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface Creeper extends Monster {
	
	public static final NBTSerializationHandler<Creeper>
	NBT_HANDLER = NBTSerializationHandler
					.builder(Creeper.class)
					.include(Creeper.NBT_HANDLER)
					.byteField("ExplosionRadius", Creeper::getExplosionRadius, Creeper::setExplosionRadius, (byte) 3)
					.shortField("Fuse", Creeper::getFuseTime, Creeper::setFuseTime, (short) 30)
					.boolField("ignited", Creeper::isIgnited, Creeper::setIgnited, false)
					.boolField("powered", Creeper::isChared, Creeper::setChared, false)
					.boolField("Fusing", Creeper::isFusing, Creeper::setFusing, false) // non standard
					.build();
	
	/**
	 * Returns the time in ticks until the {@link Creeper} explodes
	 * @return time
	 */
	int getFuseTime();
	
	/**
	 * Sets the time till the {@link Creeper} explodes
	 * @param fuze
	 */
	void setFuseTime(int fuze);
	
	boolean isChared();
	
	void setChared(boolean charged);
	
	boolean isIgnited();
	
	void setIgnited(boolean ignited);

	/**
	 * Returns whether or not the {@link Creeper} is displayed as fusing
	 * @return true if fusing
	 */
	boolean isFusing();
	
	/**
	 * Sets whether or no the {@link Creeper} should be displayed as fusing
	 * @param fuzing
	 */
	void setFusing(boolean fuzing);

	/**
	 * Sets the radius of explosion. Values lower or equal to 0 will result in no destruction.
	 * @param radius
	 */
	void setExplosionRadius(int radius);
	
	int getExplosionRadius();
	
	@Override
	default NBTSerializationHandler<? extends LivingEntity> getNBTHandler() {
		return NBT_HANDLER;
	}
	
}
