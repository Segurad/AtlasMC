package de.atlascore.proxy;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import de.atlasmc.event.Event;
import de.atlasmc.util.AbstractTickingThread;

public class CoreProxyThread extends AbstractTickingThread {

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
