package de.atlascore.server;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import de.atlasmc.Atlas;
import de.atlasmc.event.Event;
import de.atlasmc.event.HandlerList;
import de.atlasmc.scheduler.Scheduler;
import de.atlasmc.util.TickingThread;
import de.atlasmc.world.World;

public class CoreServerThread extends TickingThread {
	
	private final CoreLocalServer server;
	private final Queue<Event> eventQueue;
	private final Scheduler scheduler;
	
	public CoreServerThread(CoreLocalServer server) {
		super("ServerThread: " + server.getServerID(), 50);
		this.eventQueue = new ConcurrentLinkedQueue<>();
		this.server = server;
		this.scheduler = Atlas.getScheduler().createScheduler();
	}

	public void queueEvent(Event event) {
		eventQueue.add(event);
	}

	public Scheduler getScheduler() {
		return scheduler;
	}

	@Override
	protected void tick() {
		while(!eventQueue.isEmpty()) {
			Event event = eventQueue.poll();
			HandlerList.callEvent(event);
		}
		for (World w : server.getWorlds()) {
			w.tick();
		}
		scheduler.runNextTasks();
	}

}
