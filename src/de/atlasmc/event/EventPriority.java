package de.atlasmc.event;

import java.util.List;

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
	MONITOR;
	
	private static List<EventPriority> VALUES;
	
	/**
	 * Returns a immutable List of all Types.<br>
	 * This method avoid allocation of a new array not like {@link #values()}.
	 * @return list
	 */
	public static List<EventPriority> getValues() {
		if (VALUES == null)
			VALUES = List.of(values());
		return VALUES;
	}
	
	/**
	 * Releases the system resources used from the values cache
	 */
	public static void freeValues() {
		VALUES = null;
	}
	
}
