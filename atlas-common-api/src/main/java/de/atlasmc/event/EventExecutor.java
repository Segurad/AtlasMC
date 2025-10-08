package de.atlasmc.event;

import de.atlasmc.plugin.PluginHandle;
import de.atlasmc.util.annotation.NotNull;

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
		public void fireEvent(Event event) {
			// not required
		}

		@Override
		public PluginHandle getPlugin() {
			return null;
		}
	};
	
	/**
	 * Returns the listener this handler belongs to
	 * @return listener
	 */
	@NotNull
	Listener getListener();

	/**
	 * Returns whether or not this handler is ignored when the event is canclled
	 * @return true if ignore
	 */
	boolean getIgnoreCancelled();
	
	/**
	 * Returns the event class this handler handles
	 * @return
	 */
	@NotNull
	Class<? extends Event> getEventClass();
	
	/**
	 * Returns the {@link EventPriority} for the handler
	 * @return priority
	 */
	@NotNull
	EventPriority getPriority();
	
	/**
	 * Returns the {@link PluginHandle} this handler belongs to
	 * @return handle
	 */
	PluginHandle getPlugin();
	
	/**
	 * Invokes the EventHandler Method of this EventExecutor
	 * @param event
	 * @throws Exception 
	 */
	void fireEvent(Event event) throws Exception;

}
