package de.atlasmc.event.player;

import de.atlasmc.entity.Player;
import de.atlasmc.event.Cancellable;
import de.atlasmc.event.ServerHandlerList;

public class AsyncPlayerChatEvent extends PlayerEvent implements Cancellable {

	private static final ServerHandlerList handlers = new ServerHandlerList();
	
	private String msg;
	private boolean cancelled;
	
	public AsyncPlayerChatEvent(boolean async, Player player, String msg) {
		super(async, player);
		this.msg = msg;
	}
	
	public void setMessage(String msg) {
		this.msg = msg;
	}
	
	public String getMessage() {
		return msg;
	}

	@Override
	public ServerHandlerList getHandlers() {
		return handlers;
	}
	
	public static ServerHandlerList getHandlerList() {
		return handlers;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

}
