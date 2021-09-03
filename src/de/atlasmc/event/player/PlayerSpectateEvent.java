package de.atlasmc.event.player;

import java.util.UUID;

import de.atlasmc.entity.Player;
import de.atlasmc.event.Cancellable;
import de.atlasmc.event.ServerHandlerList;

public class PlayerSpectateEvent extends PlayerEvent implements Cancellable {

	private static final ServerHandlerList handlers = new ServerHandlerList();
	
	private final UUID uuid;
	private boolean cancelled;
	
	public PlayerSpectateEvent(Player player, UUID uuid) {
		super(player);
		this.uuid = uuid;
	}
	
	public UUID getTarget() {
		return uuid;
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
