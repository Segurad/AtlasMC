package de.atlasmc.event.player;

import de.atlasmc.entity.Player;
import de.atlasmc.event.Cancellable;
import de.atlasmc.event.ServerHandlerList;

public class PlayerHeldItemChangeEvent extends PlayerEvent implements Cancellable {

	private static final ServerHandlerList handlers = new ServerHandlerList();
	
	private final int newSlot, oldSlot;
	private boolean cancelled;
	
	public PlayerHeldItemChangeEvent(Player player, int newSlot, int oldSlot) {
		super(player);
		this.newSlot = newSlot;
		this.oldSlot = oldSlot;
	}
	
	public int getNewSlot() {
		return newSlot;
	}
	
	public int getOldSlot() {
		return oldSlot;
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
