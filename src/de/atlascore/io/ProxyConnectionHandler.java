package de.atlascore.io;

import de.atlasmc.atlasnetwork.proxy.LocalProxy;
import de.atlasmc.io.Protocol;
import io.netty.channel.socket.SocketChannel;

public class ProxyConnectionHandler extends ConnectionHandler {

	private final LocalProxy proxy;
	
	public ProxyConnectionHandler(SocketChannel channel, LocalProxy proxy) {
		super(channel);
		this.proxy = proxy;
	}
	
	public ProxyConnectionHandler(SocketChannel channel, Protocol protocol, LocalProxy proxy) {
		super(channel, protocol);
		this.proxy = proxy;
	}
	
	public LocalProxy getProxy() {
		return proxy;
	}

}
