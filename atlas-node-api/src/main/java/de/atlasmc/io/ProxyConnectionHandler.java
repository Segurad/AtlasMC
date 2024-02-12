package de.atlasmc.io;

import de.atlasmc.atlasnetwork.proxy.LocalProxy;

public interface ProxyConnectionHandler extends ConnectionHandler {
	
	LocalProxy getProxy();
	
}
