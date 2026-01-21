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
	PRE_MONITOR(false),
	/**
	 * Priority to observe the outcome of the event after the default event handler made all operations
	 */
	MONITOR(false);
	
	public final boolean ignoreHandled;
	
	private EventPriority() {
		this(true);
	}
	
	private EventPriority(boolean ignoreHandled) {
		this.ignoreHandled = ignoreHandled;
	}
	
	public boolean getIgnoreHandled() {
		return ignoreHandled;
	}
	
	public boolean getIgnoreHandled(EventHandledAction action) {
		switch(action) {
		case DEFAULT:
			return ignoreHandled;
		case IGNORE:
			return true;
		case PROCESS:
			return true;
		default:
			throw new IllegalStateException();
		}
	}
	
}
