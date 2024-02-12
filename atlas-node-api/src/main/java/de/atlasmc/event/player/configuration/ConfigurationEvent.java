package de.atlasmc.event.player.configuration;

import de.atlasmc.entity.Player;
import de.atlasmc.event.player.PlayerEvent;

public abstract class ConfigurationEvent extends PlayerEvent {
	
	public ConfigurationEvent(boolean async, Player player) {
		super(async, player);
	}

}
