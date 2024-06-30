package de.atlasmc.event.player;

import de.atlasmc.entity.Player;
import de.atlasmc.event.Cancellable;
import de.atlasmc.event.ServerHandlerList;

public class PlayerToggleFlightEvent extends PlayerEvent implements Cancellable {

	private static final ServerHandlerList handlers = new ServerHandlerList();
	
	private boolean flying;
	private boolean cancelled;
	
	public PlayerToggleFlightEvent(Player player, boolean flying) {
		super(player);
		this.flying = flying;
	}
	
	public boolean isFlying() {
		return flying;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;	
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public ServerHandlerList getHandlers() {
		return handlers;
	}
	
	public static ServerHandlerList getHandlerList() {
		return handlers;
	}

}
