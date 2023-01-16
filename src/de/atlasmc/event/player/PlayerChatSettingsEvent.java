package de.atlasmc.event.player;

import de.atlasmc.chat.ChatType;
import de.atlasmc.entity.Player;
import de.atlasmc.event.ServerHandlerList;

public class PlayerChatSettingsEvent extends PlayerEvent {

	private static final ServerHandlerList HANDLERS = new ServerHandlerList();
	
	private final boolean color;
	private final ChatType mode;
	
	public PlayerChatSettingsEvent(Player player, boolean color, ChatType mode) {
		super(player);
		this.color = color;
		this.mode = mode;
	}
	
	public boolean getColor() {
		return color;
	}
	
	public ChatType getMode() {
		return mode;
	}
	
	@Override
	public ServerHandlerList getHandlers() {
		return HANDLERS;
	}
	
	public static ServerHandlerList getHandlerList() {
		return HANDLERS;
	}

}
