package de.atlasmc.event.player;

import de.atlasmc.entity.Player;
import de.atlasmc.event.ServerHandlerList;

public class PlayerToggleSprintEvent extends PlayerEvent {

	private static final ServerHandlerList handlers = new ServerHandlerList();
	
	private boolean sprinting;
	
	public PlayerToggleSprintEvent(Player player, boolean sprinting) {
		super(player);
		this.sprinting = sprinting;
	}
	
	public boolean isSprinting() {
		return sprinting;
	}
	
	public void setSprinting(boolean sprinting) {
		this.sprinting = sprinting;
	}
	
	@Override
	public ServerHandlerList getHandlers() {
		return handlers;
	}
	
	public static ServerHandlerList getHandlerList() {
		return handlers;
	}

}
