package de.atlasmc.event.player;

import de.atlasmc.entity.Player;
import de.atlasmc.event.ServerHandlerList;
import de.atlasmc.recipe.BookType;

public class PlayerSetRecipeBookStateEvent extends PlayerEvent {

	private static final ServerHandlerList handlers = new ServerHandlerList();
	
	private final BookType type;
	private final boolean open;
	private final boolean filter;
	
	public PlayerSetRecipeBookStateEvent(Player player, BookType type, boolean open, boolean filter) {
		super(player);
		this.type = type;
		this.open = open;
		this.filter = filter;
	}
	
	public BookType getBookType() {
		return type;
	}
	
	public boolean isOpen() {
		return open;
	}
	
	public boolean hasFilter() {
		return filter;
	}

	@Override
	public ServerHandlerList getHandlers() {
		return handlers;
	}
	
	public static ServerHandlerList getHandlerList() {
		return handlers;
	}
	
}
