package de.atlasmc.event.socket;

import de.atlasmc.event.ProxyHandlerList;
import de.atlasmc.io.ConnectionHandler;

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
