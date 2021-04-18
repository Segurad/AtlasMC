package de.atlasmc.event.proxy;

import de.atlasmc.atlasnetwork.proxy.LocalProxy;
import de.atlasmc.event.GenericEvent;
import de.atlasmc.event.ProxyHandlerList;
import de.atlasmc.io.ConnectionHandler;

public class PlayerLoginAtemptEvent extends GenericEvent<LocalProxy, ProxyHandlerList> {

	private static final ProxyHandlerList handlers = new ProxyHandlerList();
	
	private final ConnectionHandler handler;
	
	public PlayerLoginAtemptEvent(ConnectionHandler handler) {
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
