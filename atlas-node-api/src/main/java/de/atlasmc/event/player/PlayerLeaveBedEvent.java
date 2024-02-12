package de.atlasmc.event.player;

import de.atlasmc.entity.Player;
import de.atlasmc.event.ServerHandlerList;

/**
 * Called when a Player leaves a Bed by clicking the button
 */
public class PlayerLeaveBedEvent extends PlayerEvent {
	
	private static final ServerHandlerList handlers = new ServerHandlerList();
	
	public PlayerLeaveBedEvent(Player player) {
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
