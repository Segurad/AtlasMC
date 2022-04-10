package de.atlascore.proxy;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import de.atlasmc.event.Event;
import de.atlasmc.util.TickingThread;

public class CoreProxyThread extends TickingThread {

	private final Queue<Event> eventQueue;
	private final Queue<Runnable> syncQueue;
	
	public CoreProxyThread(CoreLocalProxy proxy) {
		super("ProxyThread", 50);
		eventQueue = new ConcurrentLinkedQueue<>();
		syncQueue = new ConcurrentLinkedQueue<>();
	}

	@Override
	protected void tick() {
		
	}

}
