package de.atlasmc.event;

public abstract class Event {

	/**
	 * Whether or not this event is asynchronous. If true this event may still be called in the right sync context.
	 */
	public final boolean isAsync;
	
	/**
	 * Whether or not this event is handled.
	 * If a event is marked as handled the outcome of this event was already handled.
	 */
	public boolean isHandled;
	
	public Event() {
		this(false);
	}
	
	public Event(boolean async) {
		this.isAsync = async;
	}
	
	/**
	 * Returns the {@link HandlerList} of this Event
	 * @return handler list
	 */
	public abstract HandlerList getHandlers();

	/**
	 * Returns the name of this event
	 * @return name
	 */
	public String getName() {
		return getClass().getSimpleName();
	}
	
}
