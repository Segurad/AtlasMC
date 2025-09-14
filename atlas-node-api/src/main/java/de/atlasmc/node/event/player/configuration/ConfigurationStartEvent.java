package de.atlasmc.node.event.player.configuration;

import de.atlasmc.node.entity.Player;
import de.atlasmc.node.event.ServerHandlerList;

public class ConfigurationStartEvent extends ConfigurationEvent {

	private static final ServerHandlerList HANDLERS = new ServerHandlerList();
	
	public ConfigurationStartEvent(boolean async, Player player) {
		super(async, player);
	}

	@Override
	public ServerHandlerList getHandlers() {
		return HANDLERS;
	}
	
	public static ServerHandlerList getHandlerList() {
		return HANDLERS;
	}

}
