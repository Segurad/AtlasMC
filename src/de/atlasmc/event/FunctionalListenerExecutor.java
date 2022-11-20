package de.atlasmc.event;

/**
 * EventExecutor implementation that provides the possibility to creates events using functional interfaces
 */
public class FunctionalListenerExecutor implements EventExecutor {

	private final Class<? extends Event> eventClass;
	private final FunctionalListener<Event> listener;
	private final boolean ignoreCancelled;
	private final EventPriority priority;
	
	public <E extends Event> FunctionalListenerExecutor(Class<E> eventClass, FunctionalListener<E> listener) {
		this(eventClass, listener, EventPriority.NORMAL, false);
	}
	
	public <E extends Event> FunctionalListenerExecutor(Class<E> eventClass, FunctionalListener<E> listener, boolean ignoreCancelled) {
		this(eventClass, listener, EventPriority.NORMAL, ignoreCancelled);
	}
	
	public <E extends Event> FunctionalListenerExecutor(Class<E> eventClass, FunctionalListener<E> listener, EventPriority priority) {
		this(eventClass, listener, priority, false);
	}
	
	@SuppressWarnings("unchecked")
	public <E extends Event> FunctionalListenerExecutor(Class<E> eventClass, FunctionalListener<E> listener, EventPriority priority, boolean ignoreCancelled) {
		this.eventClass = eventClass;
		this.priority = priority;
		this.ignoreCancelled = ignoreCancelled;
		this.listener = (FunctionalListener<Event>) listener;
	}

	@Override
	public Listener getListener() {
		return listener;
	}

	@Override
	public boolean getIgnoreCancelled() {
		return ignoreCancelled;
	}

	@Override
	public Class<? extends Event> getEventClass() {
		return eventClass;
	}

	@Override
	public EventPriority getPriority() {
		return priority;
	}

	@Override
	public void fireEvent(Event event) {
		if (!eventClass.isInstance(event)) 
			return;
		listener.accept(event);
	}
	
	/**
	 * Only checks if the obj is this
	 */
	@Override
	public final boolean equals(Object obj) {
		return obj == this;
	}
	
}
