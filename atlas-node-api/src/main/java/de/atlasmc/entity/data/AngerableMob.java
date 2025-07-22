package de.atlasmc.entity.data;

import de.atlasmc.entity.LivingEntity;

public interface AngerableMob extends LivingEntity {
	
	boolean isAngry();
	
	void setAngry(boolean angry);
	
	/**
	 * Returns the time in ticks until the angry state will be reset or 0 if non
	 * @return ticks or 0
	 */
	int getAngerTime();
	
	void setAngerTime(int ticks);

}
