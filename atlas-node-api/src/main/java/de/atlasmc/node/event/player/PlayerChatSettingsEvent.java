package de.atlasmc.node.event.player;

import de.atlasmc.chat.ChatMode;
import de.atlasmc.node.entity.Player;
import de.atlasmc.node.event.ServerHandlerList;

public class PlayerChatSettingsEvent extends PlayerEvent {

	private static final ServerHandlerList HANDLERS = new ServerHandlerList();
	
	private final boolean color;
	private final ChatMode mode;
	private final boolean allowServerListing;
	private final boolean enableTextFiltering;
	
	public PlayerChatSettingsEvent(Player player, boolean color, ChatMode mode, boolean enableTextFiltering, boolean allowServerListing) {
		super(player);
		this.color = color;
		this.mode = mode;
		this.allowServerListing = allowServerListing;
		this.enableTextFiltering = enableTextFiltering;
	}
	
	public boolean getChatColor() {
		return color;
	}
	
	public ChatMode getChatMode() {
		return mode;
	}
	
	public boolean getAllowServerListing() {
		return allowServerListing;
	}
	
	public boolean hasTextFiltering() {
		return enableTextFiltering;
	}
	
	@Override
	public ServerHandlerList getHandlers() {
		return HANDLERS;
	}
	
	public static ServerHandlerList getHandlerList() {
		return HANDLERS;
	}

}
