package de.atlascore.server;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;

import de.atlasmc.Atlas;
import de.atlasmc.atlasnetwork.NodePlayer;
import de.atlasmc.event.Event;
import de.atlasmc.event.HandlerList;
import de.atlasmc.scheduler.Scheduler;
import de.atlasmc.server.NodeServer;
import de.atlasmc.util.TickingThread;
import de.atlasmc.util.concurrent.future.CompletableFuture;
import de.atlasmc.util.concurrent.future.Future;
import de.atlasmc.world.World;

public class CoreServerThread extends TickingThread {
	
	private final CoreLocalServer server;
	private final Queue<Event> eventQueue;
	private final Scheduler scheduler;
	private volatile CompletableFuture<Boolean> future;
	private final LinkedHashSet<Consumer<NodeServer>> shutdownHooks;
	
	public CoreServerThread(CoreLocalServer server, LinkedHashSet<Consumer<NodeServer>> shutdownHooks) {
		super(server.getServerName(), 50, server.getLogger(), false);
		this.eventQueue = new ConcurrentLinkedQueue<>();
		this.server = server;
		this.shutdownHooks = shutdownHooks;
		this.scheduler = Atlas.getScheduler().createScheduler();
	}

	public void queueEvent(Event event) {
		if (isRunning())
		eventQueue.add(event);
	}

	public Scheduler getScheduler() {
		return scheduler;
	}

	@Override
	protected void tick(int tick) {
		while(!eventQueue.isEmpty()) {
			Event event = eventQueue.poll();
			HandlerList.callEvent(event);
		}
		final Collection<NodePlayer> players = server.getPlayers();
		if (!players.isEmpty()) {
			for (NodePlayer player : players) {
				player.getConnection().handleSyncPackets(logger);
			}
		}
		final Collection<World> worlds = server.getWorlds();
		if (worlds.isEmpty()) {
			for (World w : worlds) {
				w.tick();
			}
		}
		scheduler.runNextTasks();
	}
	
	@Override
	public void run() {
		// startup sequence
		server.onStartup();
		// start ticking
		super.run();
		// shutdown sequence
		server.onShutdown();
		scheduler.shutdown();
		eventQueue.clear();
	}

	public Future<Boolean> startServer() {
		Future<Boolean> future = this.future = new CompletableFuture<>();
		start();
		return future;
	}

}
