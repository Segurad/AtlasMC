package de.atlasmc.node.event.player;

import de.atlasmc.node.entity.Player;
import de.atlasmc.node.event.ServerHandlerList;

public class PlayerLoadedEvent extends PlayerEvent {

	private static final ServerHandlerList HANDLERS = new ServerHandlerList();
	
	public PlayerLoadedEvent(Player player) {
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
