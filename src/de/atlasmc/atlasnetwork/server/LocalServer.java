package de.atlasmc.atlasnetwork.server;

import java.util.List;

import de.atlasmc.entity.Player;
import de.atlasmc.event.Event;

public interface LocalServer extends Server {
	
	public List<Player> getPlayers();
	public void queueEvent(Event event);
	
}
