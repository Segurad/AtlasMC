package de.atlasmc.node.event.player;

import de.atlasmc.node.entity.Player;
import de.atlasmc.node.event.ServerHandlerList;

public class PlayerChangeDisplayedSkinPartsEvent extends PlayerEvent {

	private static final ServerHandlerList HANDLERS = new ServerHandlerList();
	
	private final int newSkinParts;
	private final int oldSkinParts;
	
	public PlayerChangeDisplayedSkinPartsEvent(Player player, int newSkinParts, int oldSkinParts) {
		super(player);
		this.newSkinParts = newSkinParts;
		this.oldSkinParts = oldSkinParts;
	}
	
	public int getNewSkinParts() {
		return newSkinParts;
	}
	
	public int getOldSkinParts() {
		return oldSkinParts;
	}
	
	@Override
	public ServerHandlerList getHandlers() {
		return HANDLERS;
	}
	
	public static ServerHandlerList getHandlerList() {
		return HANDLERS;
	}

}
