package de.atlasmc.node.event.player;

import de.atlasmc.node.entity.Player;
import de.atlasmc.node.event.ServerHandlerList;

public class PlayerRespawnEvent extends PlayerEvent {

	private static final ServerHandlerList handlers = new ServerHandlerList();
	
	public PlayerRespawnEvent(Player player) {
		super(player);
	}

	@Override
	public ServerHandlerList getHandlers() {
		return handlers;
	}
	
	public static ServerHandlerList getHandlerList() {
		return handlers;
	}

}
