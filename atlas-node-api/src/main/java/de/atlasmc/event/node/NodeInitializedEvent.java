package de.atlasmc.event.node;

import de.atlasmc.event.Event;
import de.atlasmc.event.EventExecutor;
import de.atlasmc.event.HandlerList;

/**
 * Called when the node is initialized and up and running
 */
public class NodeInitializedEvent extends Event {

	private static final HandlerList HANDLERS = new HandlerList();
	
	static {
		HANDLERS.setDefaultExecutor(EventExecutor.NULL_EXECUTOR);
	}
	
	@Override
	public HandlerList getHandlers() {
		return HANDLERS;
	}
	
	public static HandlerList getHandlerList() {
		return HANDLERS;
	}

}
