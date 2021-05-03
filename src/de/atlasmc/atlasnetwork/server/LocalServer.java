package de.atlasmc.atlasnetwork.server;

import java.util.List;

import de.atlasmc.entity.Player;
import de.atlasmc.event.Event;
import de.atlasmc.scheduler.Scheduler;
import de.atlasmc.util.annotation.ThreadSafe;

public interface LocalServer extends Server {
	
	public List<Player> getPlayers();
	@ThreadSafe
	public void queueEvent(Event event);
	@ThreadSafe
	public Scheduler getScheduler();
	public boolean isServerThread();
	public long getAge();
	
}
