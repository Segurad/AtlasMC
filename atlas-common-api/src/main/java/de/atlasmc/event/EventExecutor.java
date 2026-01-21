package de.atlasmc.event;

import java.util.Objects;

import de.atlasmc.plugin.PluginHandle;
import de.atlasmc.util.annotation.NotNull;

/**
 * This class contains informations of a {@link EventHandler}
 */
public abstract class EventExecutor {
	
	/**
	 * Whether or not this handler is ignored when the event is cancelled.
	 */
	public final boolean ignoreCancelled;
	
	/**
	 * The event class this handler handles.
	 */
	@NotNull
	public final Class<? extends Event> eventClass;
	
	/**
	 * The {@link EventPriority} for the handler.
	 */
	@NotNull
	public final EventPriority priority;
	
	/**
	 * The listener this handler belongs to.
	 */
	@NotNull
	public final Listener listener;
	
	/**
	 * The {@link PluginHandle} this handler belongs to.
	 */
	@NotNull
	public final PluginHandle plugin;
	
	/**
	 * Whether or not this handler is ignored when the event is handled.
	 */
	public final boolean ignoreHandled;
	
	public EventExecutor(PluginHandle plugin, Class<? extends Event> eventClass, boolean ignoreCancelled, EventPriority priority, EventHandledAction action, Listener listener) {
		this.plugin = Objects.requireNonNull(plugin, "plugin");
		this.eventClass = Objects.requireNonNull(eventClass, "class");
		this.ignoreCancelled = ignoreCancelled;
		this.priority = Objects.requireNonNull(priority, "priority");
		this.listener = Objects.requireNonNull(listener, "listener");
		this.ignoreHandled = priority.getIgnoreHandled(action);
	}

	/**
	 * Invokes the EventHandler Method of this EventExecutor
	 * @param event
	 * @return 
	 * @throws Exception 
	 */
	public abstract void fireEvent(Event event) throws Exception;

}
