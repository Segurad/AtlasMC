package de.atlasmc.node.server;

import java.io.File;
import java.util.Collection;

import de.atlasmc.network.server.BaseServer;
import de.atlasmc.network.server.ServerGroup;
import de.atlasmc.node.NodePlayer;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.annotation.Nullable;
import de.atlasmc.util.annotation.ThreadSafe;
import de.atlasmc.util.concurrent.future.Future;

/**
 * A server managed by this node
 */
public interface NodeServer extends BaseServer {
	
	@Nullable
	ServerGroup getServerGroup();
	
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
	@NotNull
	Future<Boolean> prepare();
	
}
