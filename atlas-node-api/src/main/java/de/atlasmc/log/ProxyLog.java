package de.atlasmc.log;

import de.atlasmc.proxy.LocalProxy;

public interface ProxyLog extends Log {
	
	LocalProxy getProxy();

}
