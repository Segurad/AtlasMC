package de.atlasmc.event;

import de.atlasmc.plugin.PluginHandle;

/**
 * EventExecutor implementation that provides the possibility to creates events using functional interfaces
 */
public class FunctionalListenerExecutor extends AbstractEventExecutor {
	
	public <E extends Event> FunctionalListenerExecutor(PluginHandle plugin, Class<E> eventClass, FunctionalListener<E> listener) {
		this(plugin, eventClass, listener, EventPriority.NORMAL, false);
	}
	
	public <E extends Event> FunctionalListenerExecutor(PluginHandle plugin, Class<E> eventClass, FunctionalListener<E> listener, boolean ignoreCancelled) {
		this(plugin, eventClass, listener, EventPriority.NORMAL, ignoreCancelled);
	}
	
	public <E extends Event> FunctionalListenerExecutor(PluginHandle plugin, Class<E> eventClass, FunctionalListener<E> listener, EventPriority priority) {
		this(plugin, eventClass, listener, priority, false);
	}
	
	public <E extends Event> FunctionalListenerExecutor(PluginHandle plugin, Class<E> eventClass, FunctionalListener<E> listener, EventPriority priority, boolean ignoreCancelled) {
		super(plugin, eventClass, ignoreCancelled, priority, listener);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void internalFireEvent(Event event) throws Exception {
		((FunctionalListener<Event>) getListener()).accept(event);	
	}
	
}
