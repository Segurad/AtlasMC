package de.atlascore.server;

import java.io.File;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import de.atlasmc.Atlas;
import de.atlasmc.atlasnetwork.AtlasNode;
import de.atlasmc.atlasnetwork.AtlasPlayer;
import de.atlasmc.atlasnetwork.server.ServerConfig;
import de.atlasmc.atlasnetwork.server.ServerGroup;
import de.atlasmc.event.Event;
import de.atlasmc.log.Log;
import de.atlasmc.log.Logging;
import de.atlasmc.scheduler.Scheduler;
import de.atlasmc.server.LocalServer;
import de.atlasmc.util.concurrent.future.Future;
import de.atlasmc.world.World;

public class CoreLocalServer implements LocalServer {
	
	private final ServerGroup group;
	private final Set<AtlasPlayer> players;
	private final Set<World> worlds;
	private final CoreServerThread thread;
	private final ServerConfig config;
	private final UUID serverID;
	private final Log logger;
	private final String name;
	private final File workdir;
	
	public CoreLocalServer(UUID serverID, File workdir, ServerGroup group) {
		this(serverID, workdir, group, group.getServerConfig().clone());
	}
	
	public CoreLocalServer(UUID serverID, File workdir, ServerConfig config) {
		this(serverID, workdir, null, config);
	}
	
	protected CoreLocalServer(UUID serverID, File workdir, ServerGroup group, ServerConfig config) {
		if (serverID == null)
			throw new IllegalArgumentException("Server id can not be null!");
		if (config == null)
			throw new IllegalArgumentException("Config can not be null!");
		if (workdir == null)
			throw new IllegalArgumentException("Workdir can not be null!");
		this.workdir = workdir;
		this.config = config;
		this.group = group;
		this.name = group.getName() + "-" + serverID.toString();
		this.players = new HashSet<>();
		this.worlds = new HashSet<>();
		this.serverID = serverID;
		this.logger = Logging.getLogger(this);
		thread = new CoreServerThread(this);
	}
	
	@Override
	public File getWorkdir() {
		return workdir;
	}

	@Override
	public String getImplementationName() {
		return "Atlas-Internal";
	}

	@Override
	public Collection<AtlasPlayer> getPlayers() {
		return players;
	}

	@Override
	public AtlasNode getNode() {
		return Atlas.getAtlas();
	}

	@Override
	public ServerGroup getGroup() {
		return group;
	}

	@Override
	public int getPlayerCount() {
		return players.size();
	}

	@Override
	public int getMaxPlayers() {
		return config.getSlots();
	}

	@Override
	public String getServerName() {
		return name;
	}
	
	@Override
	public UUID getServerID() {
		return serverID;
	}

	@Override
	public void queueEvent(Event event) {
		thread.queueEvent(event);
	}

	@Override
	public Collection<World> getWorlds() {
		return worlds;
	}

	@Override
	public Scheduler getScheduler() {
		return thread.getScheduler();
	}

	@Override
	public Log getLogger() {
		return logger;
	}

	@Override
	public boolean isSync() {
		return Thread.currentThread() == thread;
	}

	@Override
	public void runTask(Runnable task) {
		thread.runTask(task);
	}

	@Override
	public Future<Void> start() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Future<Void> stop() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isRunning() {
		// TODO Auto-generated method stub
		return false;
	}

}
