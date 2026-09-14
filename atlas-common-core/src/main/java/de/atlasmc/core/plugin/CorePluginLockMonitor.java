package de.atlasmc.core.plugin;

import java.lang.ref.ReferenceQueue;
import java.util.Objects;

import de.atlasmc.log.Log;
import de.atlasmc.util.annotation.ThreadSafe;

final class CorePluginLockMonitor extends Thread {
	
	private final ReferenceQueue<?> queue;
	private final Log log;
	private volatile boolean running;
	
	public CorePluginLockMonitor(ReferenceQueue<?> queue, Log log) {
		super("Plugin Lock Monitor");
		this.queue = Objects.requireNonNull(queue, "queue");
		this.log = Objects.requireNonNull(log, "log");
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
	public synchronized void shutdown() {
		if (!running)
			return;
		running = false;	
		if (getState() == State.WAITING)
			interrupt(); // cancel waiting for next enqueued reference
	}

}
