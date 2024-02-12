package de.atlasmc.event.player;

import de.atlasmc.entity.Player;
import de.atlasmc.event.ServerHandlerList;

public class AdvancementsCloseEvent extends PlayerEvent {

	private static final ServerHandlerList handlers = new ServerHandlerList();
	
	public AdvancementsCloseEvent(Player player) {
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
