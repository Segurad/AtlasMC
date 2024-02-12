package de.atlasmc.event.player;

import de.atlasmc.entity.Player;
import de.atlasmc.event.ServerHandlerList;

/**
 * Called when a player changes his local client side
 */
public class PlayerLocaleChangeEvent extends PlayerEvent {

	private static final ServerHandlerList handlers = new ServerHandlerList();
	
	private final String locale;
	
	public PlayerLocaleChangeEvent(Player player, String locale) {
		super(player);
		this.locale = locale;
	}
	
	public String getNewLocale() {
		return locale;
	}

	@Override
	public ServerHandlerList getHandlers() {
		return handlers;
	}
	
	public static ServerHandlerList getHandlerList() {
		return handlers;
	}
	
	

}
