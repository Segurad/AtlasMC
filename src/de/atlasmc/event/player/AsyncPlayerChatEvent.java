package de.atlasmc.event.player;

import de.atlasmc.entity.Player;
import de.atlasmc.event.ServerHandlerList;

public class AsyncPlayerChatEvent extends PlayerEvent {

	private static final ServerHandlerList handlers = new ServerHandlerList();
	
	public AsyncPlayerChatEvent(boolean async, Player player, String msg) {
		super(async, player);
		// TODO
	}

	@Override
	public ServerHandlerList getHandlers() {
		return handlers;
	}
	
	public static ServerHandlerList getHandlerList() {
		return handlers;
	}

}
