package de.atlasmc.server;

import java.util.ArrayList;
import java.util.List;

import de.atlasmc.Atlas;
import de.atlasmc.entity.Player;

public class AtlasServer implements Server {
	
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
		return Atlas.getNode();
	}

	@Override
	public ServerGroup getGroup() {
		return group;
	}
	
}
