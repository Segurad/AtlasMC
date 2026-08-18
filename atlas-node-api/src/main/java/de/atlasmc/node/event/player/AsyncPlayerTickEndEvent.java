package de.atlasmc.node.event.player;

import de.atlasmc.node.entity.Player;
import de.atlasmc.node.event.ServerHandlerList;

public class AsyncPlayerTickEndEvent extends PlayerEvent {

	private static final ServerHandlerList HANDLERS = new ServerHandlerList();
	
	public AsyncPlayerTickEndEvent(Player player) {
		super(true, player);
	}

	@Override
	public ServerHandlerList getHandlers() {
		return HANDLERS;
	}
	
	public static ServerHandlerList getHandlerList() {
		return HANDLERS;
	}

}
