package de.atlasmc.node.event.socket;

import de.atlasmc.node.event.SocketHandlerList;
import de.atlasmc.node.io.protocol.LoginHandler;
import de.atlasmc.node.io.socket.NodeSocket;

public class AsyncPlayerLoginAttemptEvent extends SocketEvent {

	private static final SocketHandlerList handlers = new SocketHandlerList();
	
	private final LoginHandler handler;
	
	public AsyncPlayerLoginAttemptEvent(LoginHandler handler) {
		super(true, (NodeSocket) handler.getSocket());
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

}
