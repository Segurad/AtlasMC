package de.atlasmc.atlasnetwork.server;

import java.util.List;

import org.slf4j.Logger;

import de.atlasmc.entity.Player;
import de.atlasmc.event.Event;
import de.atlasmc.event.SyncThreadHolder;
import de.atlasmc.scheduler.Scheduler;
import de.atlasmc.util.annotation.ThreadSafe;

public interface LocalServer extends Server, SyncThreadHolder {
	
	public List<Player> getPlayers();
	
	@ThreadSafe
	public void queueEvent(Event event);
	
	@ThreadSafe
	public Scheduler getScheduler();
	
	public long getAge();
	
	@ThreadSafe
	public Logger getLogger();
	
}
