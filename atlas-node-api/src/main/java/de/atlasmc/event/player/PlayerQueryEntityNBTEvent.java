package de.atlasmc.event.player;

import de.atlasmc.entity.Player;
import de.atlasmc.event.Cancellable;
import de.atlasmc.event.ServerHandlerList;

public class PlayerQueryEntityNBTEvent extends PlayerEvent implements Cancellable {

	private static final ServerHandlerList handlers = new ServerHandlerList();
	
	private final int transactionID;
	private final int entityID;
	private boolean cancelled;
	
	public PlayerQueryEntityNBTEvent(Player player, int transactionID, int entityID) {
		super(player);
		this.transactionID = transactionID;
		this.entityID = entityID;
	}
	
	public int getTransactionID() {
		return transactionID;
	}
	
	public int getEntityID() {
		return entityID;
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
