package de.atlasmc.event.player;

import de.atlasmc.entity.Player;
import de.atlasmc.event.AbstractServerEvent;
import de.atlasmc.event.ServerHandlerList;

public class AsyncPlayerChatEvent extends AbstractServerEvent {

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
