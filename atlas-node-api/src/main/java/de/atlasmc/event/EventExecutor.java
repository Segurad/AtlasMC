package de.atlasmc.event;

import de.atlasmc.plugin.Plugin;

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
		public Class<? extends Event> getEventClass() {
			return Event.class;
		}

		@Override
		public EventPriority getPriority() {
			return EventPriority.MONITOR;
		}

		@Override
		public void fireEvent(Event event) {}

		@Override
		public Plugin getPlugin() {
			return null;
		}
	};
	
	public Listener getListener();

	public boolean getIgnoreCancelled();
	
	public Class<? extends Event> getEventClass();
	
	public EventPriority getPriority();
	
	public Plugin getPlugin();
	
	/**
	 * Invokes the EventHandler Method of this EventExecutor
	 * @param event
	 * @throws Exception 
	 */
	public void fireEvent(Event event) throws Exception;

}
