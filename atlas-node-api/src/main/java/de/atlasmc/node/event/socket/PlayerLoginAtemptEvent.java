package de.atlasmc.node.event.socket;

import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.io.connection.ServerSocketConnectionHandler;
import de.atlasmc.node.event.SocketHandlerList;
import de.atlasmc.node.io.socket.NodeSocket;

public class PlayerLoginAtemptEvent extends SocketEvent {

	private static final SocketHandlerList handlers = new SocketHandlerList();
	
	private final ConnectionHandler handler;
	
	public PlayerLoginAtemptEvent(ConnectionHandler handler) {
		super((NodeSocket) ((ServerSocketConnectionHandler) handler).getSocket());
		this.handler = handler;
	}
	
	public ConnectionHandler getConnection() {
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
