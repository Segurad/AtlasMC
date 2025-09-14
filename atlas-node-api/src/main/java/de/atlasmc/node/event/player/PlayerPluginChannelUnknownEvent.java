package de.atlasmc.node.event.player;

import de.atlasmc.NamespacedKey;
import de.atlasmc.node.entity.Player;
import de.atlasmc.node.event.ServerHandlerList;
import io.netty.buffer.ByteBuf;

/**
 * Event that indicates the {@link PlayerEvent} send a plugin message to a channel that is not registered.
 */
public class PlayerPluginChannelUnknownEvent extends PlayerEvent {
	
	private static final ServerHandlerList HANDLERS = new ServerHandlerList();
	
	private final NamespacedKey channel;
	private final ByteBuf message;
	private boolean ignore;
	
	public PlayerPluginChannelUnknownEvent(boolean async, Player player, NamespacedKey channel, ByteBuf message) {
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
	
	public NamespacedKey getChannel() {
		return channel;
	}
	
	public ByteBuf getMessage() {
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
