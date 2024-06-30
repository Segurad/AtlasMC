package de.atlasmc.event.player;

import de.atlasmc.entity.Player;
import de.atlasmc.event.ServerHandlerList;

/**
 * Called when a player changes his view distances client side
 */
public class PlayerViewDistanceChangeEvent extends PlayerEvent {

	private static final ServerHandlerList HANDLERS = new ServerHandlerList();
	
	private final int newDistance;
	private final int oldDistance;
	
	public PlayerViewDistanceChangeEvent(Player player, int newDistance, int oldDistance) {
		super(false, player);
		this.newDistance = newDistance;
		this.oldDistance = oldDistance;
	}
	
	public int getNewDistance() {
		return newDistance;
	}
	
	public int getOldDistance() {
		return oldDistance;
	}
	
	@Override
	public ServerHandlerList getHandlers() {
		return HANDLERS;
	}
	
	public static ServerHandlerList getHandlerList() {
		return HANDLERS;
	}

}
