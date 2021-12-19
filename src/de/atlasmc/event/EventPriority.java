package de.atlasmc.event;

public enum EventPriority {
	/**
	 * Priority for least important operations made before all other operations
	 */
	LOWEST,
	LOW,
	/**
	 * Default priority
	 */
	NORMAL,
	HIGH,
	/**
	 * Priority for critical operations directly before the default event handler
	 */
	HIGHEST,
	/**
	 * Priority to observe the outcome of the event before the default event handler made all operations 
	 */
	PRE_MONITOR,
	/**
	 * Priority to observe the outcome of the event after the default event handler made all operations
	 */
	MONITOR
}
