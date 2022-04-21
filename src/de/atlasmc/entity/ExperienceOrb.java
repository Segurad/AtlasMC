package de.atlasmc.entity;

public interface ExperienceOrb extends Entity {

	public int getExperience();
	
	public void setExperience(int xp);

	public void setLifeTime(int ticks);
	
	/**
	 * Returns the time in ticks until this {@link ExperienceOrb} depsawns or -1 if it does not depsawn
	 * @return ticks or -1
	 */
	public int getLifeTime();
	
}
