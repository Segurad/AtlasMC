package de.atlasmc.node.event.socket;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.node.event.ProxyHandlerList;

public class PlayerLoginAtemptEvent extends SocketEvent {

	private static final ProxyHandlerList handlers = new ProxyHandlerList();
	
	private final ConnectionHandler handler;
	
	public PlayerLoginAtemptEvent(ConnectionHandler handler) {
		super(handler.getSocket());
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
