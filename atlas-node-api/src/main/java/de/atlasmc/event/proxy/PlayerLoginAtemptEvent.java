package de.atlasmc.event.proxy;

import de.atlasmc.event.ProxyHandlerList;
import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.ProxyConnectionHandler;

public class PlayerLoginAtemptEvent extends ProxyEvent {

	private static final ProxyHandlerList handlers = new ProxyHandlerList();
	
	private final ConnectionHandler handler;
	
	public PlayerLoginAtemptEvent(ProxyConnectionHandler handler) {
		super(handler.getProxy());
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
