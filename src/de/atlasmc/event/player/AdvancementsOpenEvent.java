package de.atlasmc.event.player;

import de.atlasmc.entity.Player;
import de.atlasmc.event.ServerHandlerList;

public class AdvancementsOpenEvent extends PlayerEvent {
	
	private static final ServerHandlerList handlers = new ServerHandlerList();
	
	private String tabID;
	
	public AdvancementsOpenEvent(Player player, String tabID) {
		super(player);
		this.tabID = tabID;
	}
	
	public String getTabID() {
		return tabID;
	}
	
	public void setTabID(String tabID) {
		this.tabID = tabID;
	}

	@Override
	public ServerHandlerList getHandlers() {
		return handlers;
	}
	
	public static ServerHandlerList getHandlerList() {
		return handlers;
	}

}
