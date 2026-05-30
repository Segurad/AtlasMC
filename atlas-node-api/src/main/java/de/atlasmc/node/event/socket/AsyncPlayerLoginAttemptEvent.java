package de.atlasmc.node.event.socket;

import de.atlasmc.event.Cancellable;
import de.atlasmc.node.event.SocketHandlerList;
import de.atlasmc.node.io.protocol.LoginHandler;
import de.atlasmc.node.io.socket.NodeSocket;

public class AsyncPlayerLoginAttemptEvent extends SocketEvent implements Cancellable {

	private static final SocketHandlerList handlers = new SocketHandlerList();
	
	private final LoginHandler handler;
	private boolean cancelled;
	
	public AsyncPlayerLoginAttemptEvent(boolean async, LoginHandler handler) {
		super(async, (NodeSocket) handler.getSocket());
		this.handler = handler;
	}
	
	public LoginHandler getConnection() {
		return handler;
	}

	@Override
	public SocketHandlerList getHandlers() {
		return handlers;
	}
	
	public static SocketHandlerList getHandlerList() {
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
