package de.atlasmc.factory;

import de.atlasmc.atlasnetwork.server.LocalServer;
import de.atlasmc.atlasnetwork.server.ServerConfig;

public interface ServerFactory {
	
	LocalServer createServer(ServerConfig config);

}
