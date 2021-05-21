package de.atlasmc.event.player;

import de.atlasmc.atlasnetwork.server.LocalServer;
import de.atlasmc.entity.Player;
import de.atlasmc.event.GenericEvent;
import de.atlasmc.event.ServerHandlerList;

public class AsyncPlayerChatEvent extends GenericEvent<LocalServer, ServerHandlerList> {

	public AsyncPlayerChatEvent(boolean async, Player player, String msg) {
		super(async, player.getCurrentServer());
		// TODO
	}

	@Override
	public ServerHandlerList getHandlers() {
		// TODO Auto-generated method stub
		return null;
	}

}
