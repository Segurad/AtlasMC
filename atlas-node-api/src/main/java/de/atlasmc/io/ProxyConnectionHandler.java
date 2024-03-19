package de.atlasmc.io;

import de.atlasmc.proxy.LocalProxy;

public interface ProxyConnectionHandler extends ConnectionHandler {
	
	LocalProxy getProxy();
	
}
