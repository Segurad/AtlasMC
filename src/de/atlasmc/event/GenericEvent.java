package de.atlasmc.event;

/**
 * Event which is initiated by a specific part e.g. LocalServer with PlayerMoveEvent
 */
public abstract class GenericEvent<E, H extends HandlerList> extends Event {

	private final E eventSource;
	
	public GenericEvent(E eventSource) {
		this(false, eventSource);
	}
	
	public GenericEvent(boolean async, E eventSource) {
		super(async);
		this.eventSource = eventSource;
	}
	
	public E getEventSource() {
		return eventSource;
	}
	
	@Override
	public abstract H getHandlers();
}
