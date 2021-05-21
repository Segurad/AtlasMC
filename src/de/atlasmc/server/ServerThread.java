package de.atlasmc.server;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import de.atlasmc.event.Event;
import de.atlasmc.event.HandlerList;
import de.atlasmc.scheduler.AtlasScheduler;
import de.atlasmc.scheduler.Scheduler;
import de.atlasmc.world.World;

public class ServerThread extends Thread {
	
	private final AtlasServer server;
	private boolean running;
	private final Queue<Event> eventQueue;
	private final AtlasScheduler scheduler;
	private int skipped;
	private long age;
	
	public ServerThread(AtlasServer server) {
		super("ServerThread: " + server.getServerID());
		this.eventQueue = new ConcurrentLinkedQueue<Event>();
		this.server = server;
		this.scheduler = new AtlasScheduler(server);
	}

	@Override
	public void run() {
		if (isRunning()) throw new RuntimeException("ServerThread already running!");
		running = true;
		long startTime;
		long endTime;
		long remaining;
		while (isRunning()) {
			startTime = System.currentTimeMillis();
			// Start server process;
			scheduler.runNextTasks();
			while(!eventQueue.isEmpty()) {
				Event event = eventQueue.poll();
				HandlerList.callEvent(event);
			}
			for (World w : server.getWorlds()) {
				w.tick();
			}
			age++;
			// End server process
			endTime = System.currentTimeMillis();
			remaining = endTime - startTime;
			if (remaining > 50) {
				skipped += remaining / 50;
				remaining = 50 - (remaining % 50);
			} else remaining = 50 - remaining;
			try {
				Thread.sleep(remaining);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		// Stop process
	}
	
	public boolean isRunning() {
		synchronized (this) {
			return running;
		}
	}
	
	public void stopServer() {
		synchronized (this) {
			running = false;
		}
	}
	
	public int getSkippedTicks() {
		return skipped;
	}

	public void queueEvent(Event event) {
		eventQueue.add(event);
	}

	public Scheduler getScheduler() {
		return scheduler;
	}

	public long getAge() {
		return age;
	}

}
