package de.atlasmc.cache;

import de.atlasmc.util.annotation.ThreadSafe;

/**
 * Handles all Cache processes.
 * Implementations should never keep a direct reference to a {@link CacheHolder}. 
 * Note: if {@link CacheHolder#cleanUp()} is called a reference will be inevitably created.
 */
@ThreadSafe
public interface CacheHandler {

	long getDefaultIntervall();
	
	void setDefaultIntervall(int intervall);

	/**
	 * Registers a {@link CacheHolder} with {@link #getDefaultIntervall()}
	 * @param holder to register
	 * @return true if registered
	 */
	boolean register(CacheHolder holder);
	
	boolean register(CacheHolder holder, int intervall);
	
	boolean unregister(CacheHolder holder);
	
	/**
	 * Schedules a cleanUp at the next possible time for all {@link CacheHolder} no matter whether they are scheduled or not
	 */
	void cleanUpNow();

}
