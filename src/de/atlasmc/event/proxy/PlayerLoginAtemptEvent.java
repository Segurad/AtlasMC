package de.atlasmc.event.proxy;

import de.atlascore.io.ConnectionHandler;
import de.atlascore.io.ProxyConnectionHandler;
import de.atlasmc.event.ProxyHandlerList;

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
