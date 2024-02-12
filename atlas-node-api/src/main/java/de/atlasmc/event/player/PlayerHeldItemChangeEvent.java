package de.atlasmc.event.player;

import de.atlasmc.entity.Player;
import de.atlasmc.event.Cancellable;
import de.atlasmc.event.ServerHandlerList;

/**
 * Called when a Player changes the selected Slot
 */
public class PlayerHeldItemChangeEvent extends PlayerEvent implements Cancellable {

	private static final ServerHandlerList handlers = new ServerHandlerList();
	
	private int newSlot;
	private boolean cancelled;
	
	public PlayerHeldItemChangeEvent(Player player, int newSlot) {
		super(player);
		this.newSlot = newSlot;
	}
	
	public int getNewSlot() {
		return newSlot;
	}
	
	public void setNewSlot(int newSlot) {
		this.newSlot = newSlot;
	}
	
	public int getOldSlot() {
		return getPlayer().getInventory().getHeldItemSlot();
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
