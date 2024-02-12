package de.atlasmc.log;

import de.atlasmc.atlasnetwork.proxy.LocalProxy;

public interface ProxyLog extends Log {
	
	LocalProxy getProxy();

}
