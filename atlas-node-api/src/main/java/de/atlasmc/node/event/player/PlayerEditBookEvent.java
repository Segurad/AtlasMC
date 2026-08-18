package de.atlasmc.node.event.player;

import de.atlasmc.node.entity.Player;
import de.atlasmc.node.event.ServerHandlerList;
import de.atlasmc.util.annotation.Nullable;

public class PlayerEditBookEvent extends PlayerEvent {

	private static final ServerHandlerList HANDLERS = new ServerHandlerList();

	private final int slot;
	private final String rawTitle;
	private final String[] rawPages;
	
	public PlayerEditBookEvent(Player player, int slot, String title, String[] pages) {
		super(player);
		this.slot = slot;
		this.rawTitle = title;
		this.rawPages = pages;
	}
	
	public int getSlot() {
		return slot;
	}
	
	@Nullable
	public String[] getRawPages() {
		return rawPages;
	}
	
	@Nullable
	public String getRawTitle() {
		return rawTitle;
	}
	
	@Override
	public ServerHandlerList getHandlers() {
		return HANDLERS;
	}
	
	public static ServerHandlerList getHandlerList() {
		return HANDLERS;
	}

}
