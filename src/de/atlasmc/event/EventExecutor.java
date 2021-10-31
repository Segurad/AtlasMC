package de.atlasmc.event;

/**
 * This class contains informations of a {@link EventHandler}
 */
public interface EventExecutor {
	
	/**
	 * EventExecutor that does nothing
	 */
	public static final EventExecutor NULL_EXECUTOR = new EventExecutor() {

		@Override
		public Listener getListener() {
			return null;
		}

		@Override
		public boolean getIgnoreCancelled() {
			return true;
		}

		@Override
		public Class<?> getEventClass() {
			return Event.class;
		}

		@Override
		public EventPriority getPriority() {
			return EventPriority.MONITOR;
		}

		@Override
		public void fireEvent(Event event) {}
	};
	
	public Listener getListener();

	public boolean getIgnoreCancelled();
	
	public Class<?> getEventClass();
	
	public EventPriority getPriority();
	
	/**
	 * Invokes the EventHandler Method of this EventExecutor
	 * @param event
	 */
	public void fireEvent(Event event);

}
