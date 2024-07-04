package de.atlasmc.event;

public abstract class Event {

	private final boolean async;
	
	public Event() {
		this(false);
	}
	
	public Event(boolean async) {
		this.async = async;
	}
	
	/**
	 * Returns the {@link HandlerList} of this Event
	 * @return handler list
	 */
	public abstract HandlerList getHandlers();

	/**
	 * Returns whether or not this event is async
	 * @return true if async
	 */
	public final boolean isAsynchronous() {
		return async;
	}

	public String getName() {
		return getClass().getSimpleName();
	}
	
}
