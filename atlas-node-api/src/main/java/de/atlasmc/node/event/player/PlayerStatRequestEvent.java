package de.atlasmc.node.event.player;

import de.atlasmc.node.entity.Player;
import de.atlasmc.node.event.ServerHandlerList;

public class PlayerStatRequestEvent extends PlayerEvent {

	private static final ServerHandlerList HANDLERS = new ServerHandlerList();
	
	public PlayerStatRequestEvent(Player player) {
		super(player);
	}

	@Override
	public ServerHandlerList getHandlers() {
		return HANDLERS;
	}
	
	public static ServerHandlerList getHandlerList() {
		return HANDLERS;
	}

}
