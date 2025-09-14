package de.atlasmc.node.event.player.configuration;

import de.atlasmc.node.entity.Player;
import de.atlasmc.node.event.player.PlayerEvent;

public abstract class ConfigurationEvent extends PlayerEvent {
	
	public ConfigurationEvent(boolean async, Player player) {
		super(async, player);
	}

}
