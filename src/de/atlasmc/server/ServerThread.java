package de.atlasmc.server;

import java.util.Queue;

import de.atlasmc.event.Event;
import de.atlasmc.event.HandlerList;

public class ServerThread extends Thread {
	
	private final AtlasServer server;
	private boolean running;
	private final Queue<Event> eventQueue;
	
	public ServerThread(AtlasServer server) {
		this.server = server;
	}

	@Override
	public void run() {
		long startTime = System.currentTimeMillis();
		while (true) {
			while(!eventQueue.isEmpty()) {
				Event event = eventQueue.poll();
				HandlerList.callEvent(event);
			}
		}
	}

	public void queueEvent(Event event) {
		eventQueue.add(event);
	}

}
