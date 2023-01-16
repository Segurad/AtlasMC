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
		if (eventSource == null)
			throw new IllegalArgumentException("Event source can not be null!");
		this.eventSource = eventSource;
	}
	
	public E getEventSource() {
		return eventSource;
	}
	
	@Override
	public abstract H getHandlers();
	
	/**
	 * Returns the thread holder for synchronous events. May be the same as {@link #getEventSource()}
	 * @return holder
	 */
	public abstract SyncThreadHolder getSyncThreadHolder();
	
}
