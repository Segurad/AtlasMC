package de.atlascore.server;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;

import de.atlasmc.Atlas;
import de.atlasmc.atlasnetwork.AtlasNode;
import de.atlasmc.atlasnetwork.server.LocalServer;
import de.atlasmc.atlasnetwork.server.ServerConfig;
import de.atlasmc.atlasnetwork.server.ServerGroup;
import de.atlasmc.entity.Player;
import de.atlasmc.event.Event;
import de.atlasmc.scheduler.Scheduler;
import de.atlasmc.world.World;

public class CoreLocalServer implements LocalServer {
	
	private final ServerGroup group;
	private final List<Player> players;
	private final CoreServerThread thread;
	private final List<World> worlds;
	private final ServerConfig config;
	private final int serverID;
	private final Logger logger;
	
	protected CoreLocalServer(int serverID, ServerGroup group) {
		this.config = group.getServerConfig().clone();
		this.group = group;
		this.players = new ArrayList<>();
		thread = new CoreServerThread(this);
		this.worlds = new ArrayList<>();
		this.serverID = serverID;
		this.logger = null; // TODO logger
	}

	@Override
	public String getIdentifier() {
		return "Atlas";
	}

	@Override
	public List<Player> getPlayers() {
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
	public int getServerID() {
		return serverID;
	}

	@Override
	public void queueEvent(Event event) {
		thread.queueEvent(event);
	}

	public List<World> getWorlds() {
		return worlds;
	}

	@Override
	public Scheduler getScheduler() {
		return thread.getScheduler();
	}

	@Override
	public long getAge() {
		return thread.getTick();
	}

	@Override
	public Logger getLogger() {
		return logger;
	}

	@Override
	public boolean isSync() {
		return Thread.currentThread() == thread;
	}

}
