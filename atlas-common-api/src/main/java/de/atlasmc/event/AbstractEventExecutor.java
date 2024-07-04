package de.atlasmc.event;

import de.atlasmc.plugin.Plugin;

public abstract class AbstractEventExecutor implements EventExecutor {

	private final boolean ignorecancelled;
	private final Class<? extends Event> eventClass;
	private final EventPriority priority;
	private final Listener listener;
	private final Plugin plugin;
	
	public AbstractEventExecutor(Plugin plugin, Class<? extends Event> eventClass, boolean ignoreCancelled, EventPriority priority, Listener listener) {
		if (plugin == null)
			throw new IllegalArgumentException("Plugin can not be null!");
		if (eventClass == null)
			throw new IllegalArgumentException("Event class can not be null!");
		if (priority == null)
			throw new IllegalArgumentException("Priority can not be null!");
		if (listener == null)
			throw new IllegalArgumentException("Listener can not be null!");
		this.plugin = plugin;
		this.eventClass = eventClass;
		this.ignorecancelled = ignoreCancelled;
		this.priority = priority;
		this.listener = listener;
	}
	
	@Override
	public Listener getListener() {
		return listener;
	}

	@Override
	public boolean getIgnoreCancelled() {
		return ignorecancelled;
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
	public Plugin getPlugin() {
		return plugin;
	}

	@Override
	public void fireEvent(Event event) throws Exception {
		if (!eventClass.isInstance(event))
			return;
		internalFireEvent(event);
	}
	
	/**
	 * Called to fire the event. Only called when the event is a instance of the event class
	 * @param event
	 * @throws Exception 
	 */
	protected abstract void internalFireEvent(Event event) throws Exception;

}
