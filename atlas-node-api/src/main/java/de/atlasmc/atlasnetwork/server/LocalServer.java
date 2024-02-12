package de.atlasmc.atlasnetwork.server;

import java.io.File;
import java.util.Collection;

import de.atlasmc.entity.Player;
import de.atlasmc.event.Event;
import de.atlasmc.event.SyncThreadHolder;
import de.atlasmc.log.Log;
import de.atlasmc.scheduler.Scheduler;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.annotation.ThreadSafe;
import de.atlasmc.util.concurrent.future.Future;
import de.atlasmc.world.World;

public interface LocalServer extends Server, SyncThreadHolder {
	
	@NotNull
	Collection<Player> getPlayers();
	
	@NotNull
	Collection<World> getWorlds();
	
	@ThreadSafe
	void queueEvent(Event event);
	
	@ThreadSafe
	@NotNull
	Scheduler getScheduler();
	
	long getAge();
	
	@ThreadSafe
	@NotNull
	Log getLogger();
	
	/**
	 * Runs the task sync with this server thread
	 * @param task to run
	 */
	@ThreadSafe
	void runTask(Runnable task);
	
	@ThreadSafe
	@NotNull
	File getWorkdir();
	
	@ThreadSafe
	@NotNull
	Future<Void> start();
	
	@ThreadSafe
	@NotNull
	Future<Void> stop();
	
	@ThreadSafe
	boolean isRunning();
	
}
