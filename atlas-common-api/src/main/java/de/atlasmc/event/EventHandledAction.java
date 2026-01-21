package de.atlasmc.event;

public enum EventHandledAction {
	
	/**
	 * A handler is ignored if the event is handled
	 */
	IGNORE,
	/**
	 * A handler will always process the event
	 */
	PROCESS,
	/**
	 * The default behavior based on the {@link EventPriority} is used
	 */
	DEFAULT

}
