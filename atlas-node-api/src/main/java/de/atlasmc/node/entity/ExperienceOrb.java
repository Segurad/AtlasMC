package de.atlasmc.node.entity;

import de.atlasmc.nbt.codec.NBTCodec;

public interface ExperienceOrb extends Entity {
	
	public static final NBTCodec<ExperienceOrb>
	NBT_HANDLER = NBTCodec
					.builder(ExperienceOrb.class)
					.include(Entity.NBT_CODEC)
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
	default NBTCodec<? extends ExperienceOrb> getNBTCodec() {
		return NBT_HANDLER;
	}
	
}
