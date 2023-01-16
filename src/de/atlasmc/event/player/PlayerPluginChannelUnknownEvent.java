package de.atlasmc.event.player;

import de.atlasmc.entity.Player;
import de.atlasmc.event.ServerHandlerList;

/**
 * Event that indicates the {@link PlayerEvent} send a plugin message to a channel that is not registered.
 */
public class PlayerPluginChannelUnknownEvent extends PlayerEvent {
	
	private static final ServerHandlerList HANDLERS = new ServerHandlerList();
	
	private final String channel;
	private final byte[] message;
	private boolean ignore;
	
	public PlayerPluginChannelUnknownEvent(boolean async, Player player, String channel, byte[] message) {
		super(async, player);
		this.channel = channel;
		this.message = message;
	}
	
	/**
	 * Whether or not this message should be ignored or the player should be kicked
	 * @return ignore
	 */
	public boolean isIgnore() {
		return ignore;
	}
	
	public void setIgnore(boolean ignore) {
		this.ignore = ignore;
	}
	
	public String getChannel() {
		return channel;
	}
	
	public byte[] getMessage() {
		return message;
	}

	@Override
	public ServerHandlerList getHandlers() {
		return HANDLERS;
	}
	
	public static ServerHandlerList getHandlerList() {
		return HANDLERS;
	}

}
