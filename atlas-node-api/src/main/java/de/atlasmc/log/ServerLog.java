package de.atlasmc.log;

import de.atlasmc.atlasnetwork.server.LocalServer;

public interface ServerLog extends Log {
	
	LocalServer getServer();

}
