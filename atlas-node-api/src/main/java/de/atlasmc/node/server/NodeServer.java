package de.atlasmc.node.server;

import java.io.File;
import java.util.Collection;

import de.atlasmc.network.server.Server;
import de.atlasmc.node.NodePlayer;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.annotation.ThreadSafe;
import de.atlasmc.util.concurrent.future.Future;

/**
 * A server managed by this node
 */
public interface NodeServer extends Server {
	
	@NotNull
	Collection<NodePlayer> getPlayers();
	

	@ThreadSafe
	@NotNull
	File getWorlddir();
	
	@ThreadSafe
	@NotNull
	File getWorkdir();
	
	@ThreadSafe
	@NotNull
	Future<Boolean> start();
	
	@ThreadSafe
	@NotNull
	Future<Boolean> stop();
	
	@ThreadSafe
	boolean isRunning();

	@ThreadSafe
	@NotNull
	Future<Boolean> prepare();
	
}
