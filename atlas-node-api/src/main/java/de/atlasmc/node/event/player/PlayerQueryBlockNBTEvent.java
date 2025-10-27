package de.atlasmc.node.event.player;

import de.atlasmc.event.Cancellable;
import de.atlasmc.node.WorldLocation;
import de.atlasmc.node.entity.Player;
import de.atlasmc.node.event.ServerHandlerList;

public class PlayerQueryBlockNBTEvent extends PlayerEvent implements Cancellable {

	private static final ServerHandlerList handlers = new ServerHandlerList();
	
	private final WorldLocation loc;
	private final int transactionID;
	private boolean cancelled;
	
	public PlayerQueryBlockNBTEvent(Player player, int transactionID, WorldLocation loc) {
		super(player);
		this.transactionID = transactionID;
		this.loc = loc;
	}
	
	public WorldLocation getLocation() {
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
