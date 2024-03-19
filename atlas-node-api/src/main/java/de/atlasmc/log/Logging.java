package de.atlasmc.log;

import de.atlasmc.plugin.Plugin;
import de.atlasmc.proxy.LocalProxy;
import de.atlasmc.server.LocalServer;

public class Logging {
	
	private static volatile LogHandler HANDLER;
	
	public static Log getLogger(String name, String group) {
		return HANDLER.getLogger(name, group);
	}
	
	public static ServerLog getLogger(LocalServer server) {
		return HANDLER.getLogger(server);
	}
	
	public static ProxyLog getLogger(LocalProxy proxy) {
		return HANDLER.getLogger(proxy);
	}
	
	public static PluginLog getLogger(Plugin plugin) {
		return HANDLER.getLogger(plugin);
	}
	
	public static void init(LogHandler handler) {
		if (handler == null)
			throw new IllegalArgumentException("Handler can not be null!");
		synchronized (Logging.class) {
			if (HANDLER != null)
				throw new IllegalStateException("LoggingUtil already initialized!");
			HANDLER = handler;
		}
	}

}
