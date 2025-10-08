package de.atlasmc.node.server;

import java.util.Collection;
import java.util.Queue;

import de.atlasmc.event.Event;
import de.atlasmc.event.SyncThreadHolder;
import de.atlasmc.log.Log;
import de.atlasmc.log.LogHolder;
import de.atlasmc.node.world.World;
import de.atlasmc.scheduler.Scheduler;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.annotation.ThreadSafe;

/**
 * A server running as a thread in this node
 */
public interface LocalServer extends NodeServer, SyncThreadHolder, LogHolder {
	
	@NotNull
	Collection<World> getWorlds();
	
	@ThreadSafe
	void queueEvent(Event event);
	
	@ThreadSafe
	Queue<Event> getEventQueue();
	
	@ThreadSafe
	@NotNull
	Scheduler getScheduler();
	
	@ThreadSafe
	@NotNull
	Log getLogger();
	
	/**
	 * Runs the task sync with this server thread
	 * @param task to run
	 */
	@ThreadSafe
	void runTask(Runnable task);

}
