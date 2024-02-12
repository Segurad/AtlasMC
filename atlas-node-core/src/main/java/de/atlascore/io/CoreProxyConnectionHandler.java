package de.atlascore.io;

import de.atlasmc.atlasnetwork.proxy.LocalProxy;
import de.atlasmc.io.Protocol;
import de.atlasmc.io.ProxyConnectionHandler;
import de.atlasmc.log.Log;
import io.netty.channel.socket.SocketChannel;

public class CoreProxyConnectionHandler extends CoreConnectionHandler implements ProxyConnectionHandler {

	private final LocalProxy proxy;
	
	public CoreProxyConnectionHandler(SocketChannel channel, LocalProxy proxy) {
		super(channel);
		this.proxy = proxy;
	}
	
	public CoreProxyConnectionHandler(SocketChannel channel, Protocol protocol, LocalProxy proxy) {
		super(channel, protocol);
		if (proxy == null)
			throw new IllegalArgumentException("Proxy can not be null!");
		this.proxy = proxy;
	}
	
	public LocalProxy getProxy() {
		return proxy;
	}
	
	@Override
	public Log getLogger() {
		return proxy.getLogger();
	}

}
