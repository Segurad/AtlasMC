package de.atlasmc.event.player;

import de.atlasmc.NamespacedKey;
import de.atlasmc.entity.Player;
import de.atlasmc.event.ServerHandlerList;

public class AdvancementsOpenEvent extends PlayerEvent {
	
	private static final ServerHandlerList handlers = new ServerHandlerList();
	
	private NamespacedKey tabID;
	
	public AdvancementsOpenEvent(Player player, NamespacedKey tabID) {
		super(player);
		this.tabID = tabID;
	}
	
	public NamespacedKey getTabID() {
		return tabID;
	}
	
	public void setTabID(NamespacedKey tabID) {
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
