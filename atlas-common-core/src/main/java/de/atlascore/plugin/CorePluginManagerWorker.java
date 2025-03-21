package de.atlascore.plugin;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import de.atlasmc.log.Log;
import de.atlasmc.util.annotation.ThreadSafe;

final class CorePluginManagerWorker extends Thread {

	private final BlockingQueue<Runnable> tasks;
	private final Log log;
	private volatile boolean running;
	
	public CorePluginManagerWorker(Log log) {
		super("PluginManager Worker");
		this.log = log;
		this.tasks = new LinkedBlockingQueue<>();
		setDaemon(true);
	}
	
	@Override
	public void run() {
		while (running) {
			try {
				tasks.take().run();
			} catch (InterruptedException e) {
				if (running)
					log.error("Interrupted while wainting for next plugin lock!", e);
			} catch (Exception e) {
				log.error("Error while running task!", e);
			}
		}
	}
	
	public void queueTask(Runnable task) {
		if (task == null)
			throw new IllegalArgumentException("Task can not be null!");
		synchronized (this) {
			if (running)
				tasks.add(task);	
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
			tasks.clear();
		}
	}
	
}
