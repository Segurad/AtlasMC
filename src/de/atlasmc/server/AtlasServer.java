package de.atlasmc.server;

import java.util.ArrayList;
import java.util.List;
import de.atlasmc.Atlas;
import de.atlasmc.atlasnetwork.AtlasNode;
import de.atlasmc.atlasnetwork.server.LocalServer;
import de.atlasmc.atlasnetwork.server.ServerConfig;
import de.atlasmc.atlasnetwork.server.ServerGroup;
import de.atlasmc.entity.Player;
import de.atlasmc.event.Event;
import de.atlasmc.scheduler.Scheduler;
import de.atlasmc.world.World;

public class AtlasServer implements LocalServer {
	
	private final ServerGroup group;
	private final List<Player> players;
	private final ServerThread thread;
	private final List<World> worlds;
	private final ServerConfig config;
	private final int serverID;
	
	protected AtlasServer(int serverID, ServerGroup group) {
		this.config = group.getServerConfig().clone();
		this.group = group;
		this.players = new ArrayList<Player>();
		thread = new ServerThread(this);
		this.worlds = new ArrayList<World>();
		this.serverID = serverID;
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
		return config.getMaxPlayers();
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
	public boolean isServerThread() {
		return Thread.currentThread() == thread;
	}

	@Override
	public Scheduler getScheduler() {
		return thread.getScheduler();
	}

	@Override
	public long getAge() {
		return thread.getAge();
	}

}
