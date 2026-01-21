package de.atlasmc.event;

import de.atlasmc.plugin.PluginHandle;

/**
 * EventExecutor implementation that provides the possibility to creates events using functional interfaces
 */
public class FunctionalListenerExecutor extends EventExecutor {
	
	public <E extends Event> FunctionalListenerExecutor(PluginHandle plugin, Class<E> eventClass, FunctionalListener<E> listener) {
		this(plugin, eventClass, listener, EventPriority.NORMAL, EventHandledAction.DEFAULT, false);
	}
	
	public <E extends Event> FunctionalListenerExecutor(PluginHandle plugin, Class<E> eventClass, FunctionalListener<E> listener, boolean ignoreCancelled) {
		this(plugin, eventClass, listener, EventPriority.NORMAL, EventHandledAction.DEFAULT, ignoreCancelled);
	}
	
	public <E extends Event> FunctionalListenerExecutor(PluginHandle plugin, Class<E> eventClass, FunctionalListener<E> listener, EventPriority priority) {
		this(plugin, eventClass, listener, priority, EventHandledAction.DEFAULT, false);
	}
	
	public <E extends Event> FunctionalListenerExecutor(PluginHandle plugin, Class<E> eventClass, FunctionalListener<E> listener, EventPriority priority, boolean ignoreCancelled) {
		super(plugin, eventClass, ignoreCancelled, priority, EventHandledAction.DEFAULT, listener);
	}
	
	public <E extends Event> FunctionalListenerExecutor(PluginHandle plugin, Class<E> eventClass, FunctionalListener<E> listener, EventPriority priority, EventHandledAction action, boolean ignoreCancelled) {
		super(plugin, eventClass, ignoreCancelled, priority, action, listener);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void fireEvent(Event event) throws Exception {
		if (!eventClass.isInstance(event))
			return;
		((FunctionalListener<Event>) listener).accept(event);	
	}
	
}
