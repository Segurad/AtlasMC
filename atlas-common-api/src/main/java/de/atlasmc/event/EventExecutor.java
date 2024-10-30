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
		public void fireEvent(Event event) {}

		@Override
		public PluginHandle getPlugin() {
			return null;
		}
	};
	
	Listener getListener();

	boolean getIgnoreCancelled();
	
	@NotNull
	Class<? extends Event> getEventClass();
	
	@NotNull
	EventPriority getPriority();
	
	PluginHandle getPlugin();
	
	/**
	 * Invokes the EventHandler Method of this EventExecutor
	 * @param event
	 * @throws Exception 
	 */
	void fireEvent(Event event) throws Exception;

}
