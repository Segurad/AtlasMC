package de.atlascore.plugin;

import java.lang.ref.ReferenceQueue;

import de.atlasmc.log.Log;
import de.atlasmc.util.annotation.ThreadSafe;

final class CorePluginLockMonitor extends Thread {
	
	private final ReferenceQueue<?> queue;
	private final Log log;
	private volatile boolean running;
	
	public CorePluginLockMonitor(ReferenceQueue<?> queue, Log log) {
		super("Plugin Lock Monitor");
		if (queue == null)
			throw new IllegalArgumentException("Queue can not be null!");
		if (log == null)
			throw new IllegalArgumentException("Log can not be null!");
		this.queue = queue;
		this.log = log;
		setDaemon(true);
	}
	
	@Override
	public void run() {
		while (running) {
			try {
				CorePluginLock lock = (CorePluginLock) queue.remove();
				lock.plugin.clearLock(lock);
			} catch (InterruptedException e) {
				if (running)
					log.error("Interrupted while wainting for next plugin lock!", e);
			}
		}
	}
	
	@ThreadSafe
	public void shutdown() {
		if (!running)
			return;
		synchronized (this) {
			if (!running)
				return;
			running = false;	
			if (getState() == State.WAITING)
				notify(); // cancel waiting for next enqueued reference
		}
	}

}
