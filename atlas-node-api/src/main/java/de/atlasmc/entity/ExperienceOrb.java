package de.atlasmc.entity;

import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface ExperienceOrb extends Entity {
	
	public static final NBTSerializationHandler<ExperienceOrb>
	NBT_HANDLER = NBTSerializationHandler
					.builder(ExperienceOrb.class)
					.include(Entity.NBT_HANDLER)
					.shortField("Age", ExperienceOrb::getLifeTime, ExperienceOrb::setLifeTime, (short) 6000)
					.intField("Count", ExperienceOrb::getCount, ExperienceOrb::setCount, 1)
					.shortField("Health", ExperienceOrb::getHealth, ExperienceOrb::setHealth, (short) 5)
					.shortField("Value", ExperienceOrb::getExperience, ExperienceOrb::setExperience)
					.build();
	int getExperience();
	
	void setExperience(int xp);

	public void setLifeTime(int ticks);
	
	/**
	 * Returns the time in ticks until this {@link ExperienceOrb} depsawns or -1 if it does not depsawn
	 * @return ticks or -1
	 */
	int getLifeTime();
	
	int getHealth();
	
	void setHealth(int health);
	
	int getCount();
	
	void setCount(int count);
	
	@Override
	default NBTSerializationHandler<? extends ExperienceOrb> getNBTHandler() {
		return NBT_HANDLER;
	}
	
}
