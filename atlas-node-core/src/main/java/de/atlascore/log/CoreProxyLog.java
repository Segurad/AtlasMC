package de.atlascore.log;

import org.apache.logging.log4j.core.Logger;

import de.atlasmc.log.ProxyLog;
import de.atlasmc.proxy.LocalProxy;

public class CoreProxyLog extends CoreLog implements ProxyLog {

	private final LocalProxy proxy;
	
	public CoreProxyLog(LocalProxy proxy, Logger log) {
		super("Proxy", log);
		this.proxy = proxy;
	}
	
	@Override
	public LocalProxy getProxy() {
		return proxy;
	}

}
