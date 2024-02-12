package de.atlasmc.log;

import de.atlasmc.atlasnetwork.proxy.LocalProxy;
import de.atlasmc.atlasnetwork.server.LocalServer;
import de.atlasmc.plugin.Plugin;

public interface LogHandler {
	
	Log getLogger(Class<?> clazz, String group);
	
	Log getLogger(String name, String group);
	
	ServerLog getLogger(LocalServer server);
	
	PluginLog getLogger(Plugin plugin);
	
	ProxyLog getLogger(LocalProxy proxy);

}
