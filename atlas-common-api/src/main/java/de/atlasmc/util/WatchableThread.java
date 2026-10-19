package de.atlasmc.util;

import de.atlasmc.util.annotation.NotNull;

public interface WatchableThread {
	
	/**
	 * Returns the {@link System#nanoTime()} value of the last time the thread was active.
	 * Must only be compared with other {@link System#nanoTime()} values.
	 * @return nano time of the last heart beat
	 */
	long lastHeartBeat();
	
	@NotNull
	Thread getThread();

}
