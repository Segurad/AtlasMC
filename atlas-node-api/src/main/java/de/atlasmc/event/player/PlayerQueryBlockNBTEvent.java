package de.atlasmc.event.player;

import de.atlasmc.Location;
import de.atlasmc.entity.Player;
import de.atlasmc.event.Cancellable;
import de.atlasmc.event.ServerHandlerList;

public class PlayerQueryBlockNBTEvent extends PlayerEvent implements Cancellable {

	private static final ServerHandlerList handlers = new ServerHandlerList();
	
	private final Location loc;
	private final int transactionID;
	private boolean cancelled;
	
	public PlayerQueryBlockNBTEvent(Player player, int transactionID, Location loc) {
		super(player);
		this.transactionID = transactionID;
		this.loc = loc;
	}
	
	public Location getLocation() {
		return loc;
	}
	
	public int getTransactionID() {
		return transactionID;
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
