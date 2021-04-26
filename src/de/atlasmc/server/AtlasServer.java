package de.atlasmc.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import de.atlasmc.Atlas;
import de.atlasmc.atlasnetwork.AtlasNode;
import de.atlasmc.atlasnetwork.server.LocalServer;
import de.atlasmc.atlasnetwork.server.ServerGroup;
import de.atlasmc.entity.Player;
import de.atlasmc.event.Event;
import de.atlasmc.world.World;

public class AtlasServer implements LocalServer {
	
	private final ServerGroup group;
	private final List<Player> players;
	private final ServerThread thread;
	private final List<World> worlds;
	
	protected AtlasServer(ServerGroup group) {
		this.group = group;
		this.players = new ArrayList<Player>();
		thread = new ServerThread(this);
		this.worlds = new ArrayList<World>();
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
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMaxPlayers() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getServerID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void queueEvent(Event event) {
		thread.queueEvent(event);
	}

}
