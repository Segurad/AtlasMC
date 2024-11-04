package de.atlasmc.plugin.startup;

import de.atlasmc.event.Event;
import de.atlasmc.event.HandlerList;

public class StartupFinalizedEvent extends Event {

	private static final HandlerList HANDLERS = new HandlerList();
	
	@Override
	public HandlerList getHandlers() {
		return HANDLERS;
	}
	
	public static HandlerList getHandlerList() {
		return HANDLERS;
	}

}
