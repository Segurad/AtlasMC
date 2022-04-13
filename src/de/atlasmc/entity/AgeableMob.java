package de.atlasmc.entity;

import java.util.UUID;

import de.atlasmc.event.entity.EntityGrowEvent;

public interface AgeableMob extends PathfinderMob {
	
	public boolean isBaby();
	
	public void setBaby(boolean baby);
	
	/**
	 * Sets the age value of this Entity.<br>
	 * Meanings of Age:
	 * <li> age < 0 it ticks up until 0 and will turn try to turn it into an adult by calling {@link EntityGrowEvent}
	 * <li> setting the age to 0 will not call any Events and will keep the is as adult or baby
	 * <li> age > 0 is ticking down until 0 and indicates the breeding cool down
	 * @param age
	 */
	public void setAge(int age);
	
	/**
	 * Returns the age value of this Entity.<br>
	 * @return age
	 * @see #setAge(int)
	 */
	public int getAge();

	/**
	 * Sets the time in ticks this entity is in love mode.
	 * @param time
	 */
	public void setInLove(int time);

	public int isInLove();

	/**
	 * Sets the UUID of the Entity that causes the love state.
	 * @param uuid
	 */
	public void setLoveCause(UUID uuid);
	
	public UUID getLoveCause();
	
}
