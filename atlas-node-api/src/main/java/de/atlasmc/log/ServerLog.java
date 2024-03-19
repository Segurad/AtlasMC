package de.atlasmc.log;

import de.atlasmc.server.LocalServer;

public interface ServerLog extends Log {
	
	LocalServer getServer();

}
