package de.atlasmc.node.event.socket;

import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.io.connection.ServerSocketConnectionHandler;
import de.atlasmc.node.event.ProxyHandlerList;
import de.atlasmc.node.io.socket.NodeSocket;

public class PlayerLoginAtemptEvent extends SocketEvent {

	private static final ProxyHandlerList handlers = new ProxyHandlerList();
	
	private final ConnectionHandler handler;
	
	public PlayerLoginAtemptEvent(ConnectionHandler handler) {
		super((NodeSocket) ((ServerSocketConnectionHandler) handler).getSocket());
		this.handler = handler;
	}
	
	public ConnectionHandler getConnection() {
		return handler;
	}

	@Override
	public ProxyHandlerList getHandlers() {
		return handlers;
	}
	
	public static ProxyHandlerList getHandlerList() {
		return handlers;
	}

}
