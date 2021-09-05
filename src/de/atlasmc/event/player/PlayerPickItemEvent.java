package de.atlasmc.event.player;

import de.atlasmc.entity.Player;
import de.atlasmc.event.Cancellable;
import de.atlasmc.event.ServerHandlerList;

public class PlayerPickItemEvent extends PlayerEvent implements Cancellable {

	private static final ServerHandlerList handlers = new ServerHandlerList();
	
	private int slot;
	private boolean cancelled;
	
	public PlayerPickItemEvent(Player player, int slot) {
		super(player);
		this.slot = slot;
	}
	
	public int getSlot() {
		return slot;
	}
	
	public void setSlot(int slot) {
		this.slot = slot;
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
