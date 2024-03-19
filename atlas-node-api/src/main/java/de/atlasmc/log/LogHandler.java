package de.atlasmc.log;

import de.atlasmc.plugin.Plugin;
import de.atlasmc.proxy.LocalProxy;
import de.atlasmc.server.LocalServer;

public interface LogHandler {
	
	Log getLogger(Class<?> clazz, String group);
	
	Log getLogger(String name, String group);
	
	ServerLog getLogger(LocalServer server);
	
	PluginLog getLogger(Plugin plugin);
	
	ProxyLog getLogger(LocalProxy proxy);

}
