package de.atlasmc.event;

import de.atlasmc.util.annotation.ThreadSafe;

/**
 * Holder of a thread. Is used to check if a Event is fired synchronous.
 */
public interface SyncThreadHolder {

	/**
	 * Returns whether or not this current thread is sync to the holder
	 * @return true if sync
	 */
	@ThreadSafe
	boolean isSync();
	
}
