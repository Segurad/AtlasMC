package de.atlasmc.atlasnetwork.server;

import java.util.List;

import de.atlasmc.entity.Player;

public interface LocalServer extends Server {
	
	public List<Player> getPlayers();
	
}
