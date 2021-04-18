package de.atlasmc.atlasnetwork.server;

import java.util.ArrayList;
import java.util.List;

import de.atlasmc.Atlas;
import de.atlasmc.atlasnetwork.AtlasNode;
import de.atlasmc.entity.Player;

public class AtlasServer implements LocalServer {
	
	private final ServerGroup group;
	private final List<Player> players;
	
	protected AtlasServer(ServerGroup group) {
		this.group = group;
		this.players = new ArrayList<Player>();
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

}
