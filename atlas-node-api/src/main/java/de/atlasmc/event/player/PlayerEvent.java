package de.atlasmc.event.player;

import de.atlasmc.entity.Player;
import de.atlasmc.event.AbstractServerEvent;

public abstract class PlayerEvent extends AbstractServerEvent {

	private final Player player;
	
	public PlayerEvent(Player player) {
		this(false, player);
	}
	
	public PlayerEvent(boolean async, Player player) {
		super(async, player.getServer());
		this.player = player;
	}

	public Player getPlayer() {
		return player;
	}

}
