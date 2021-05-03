package de.atlasmc.event;

public abstract class Event {

	private final boolean async;
	
	public Event() {
		this(false);
	}
	
	public Event(boolean async) {
		this.async = async;
	}
	
	public abstract HandlerList getHandlers();

	public final boolean isAsynchronous() {
		return async;
	}

	public String getName() {
		return getClass().getName();
	}
	
}
