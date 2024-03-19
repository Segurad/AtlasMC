package de.atlasmc.server;

import java.io.File;
import java.util.Collection;

import de.atlasmc.atlasnetwork.AtlasPlayer;
import de.atlasmc.atlasnetwork.server.Server;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.annotation.ThreadSafe;
import de.atlasmc.util.concurrent.future.Future;

/**
 * A server managed by this node
 */
public interface NodeServer extends Server {
	
	@NotNull
	Collection<AtlasPlayer> getPlayers();
	
	@ThreadSafe
	@NotNull
	File getWorkdir();
	
	@ThreadSafe
	@NotNull
	Future<Void> start();
	
	@ThreadSafe
	@NotNull
	Future<Void> stop();
	
	@ThreadSafe
	boolean isRunning();
	
}
