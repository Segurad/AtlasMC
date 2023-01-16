package de.atlasmc.event.player;

import de.atlasmc.atlasnetwork.server.Server;
import de.atlasmc.entity.Player;
import de.atlasmc.event.ServerHandlerList;

/**
 * Called when a player disconnect from a {@link Server}
 */
public class PlayerDisconnectEvent extends PlayerEvent {
	
	private static final ServerHandlerList HANDLERS = new ServerHandlerList();
	
	private final DisconnectCause cause;
	
	public PlayerDisconnectEvent(Player player, DisconnectCause cause) {
		super(player);
		this.cause = cause;
	}
	
	public DisconnectCause getCause() {
		return cause;
	}

	@Override
	public ServerHandlerList getHandlers() {
		return HANDLERS;
	}
	
	public static ServerHandlerList getHandlerList() {
		return HANDLERS;
	}

	public static enum DisconnectCause {
		SERVER_CHANGE,
		LEAVE,
		KICK;
	}
	
}
