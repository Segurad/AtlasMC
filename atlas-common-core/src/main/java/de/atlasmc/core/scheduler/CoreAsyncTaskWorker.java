package de.atlasmc.core.scheduler;

import java.util.concurrent.atomic.AtomicInteger;

final class CoreAsyncTaskWorker extends Thread {
	
	private static final AtomicInteger COUNT = new AtomicInteger(1);
	private volatile CoreRegisteredTask task;
	private volatile long lastActiv;
	private final CoreSchedulerThread master;
	private volatile boolean running = true;
	
	/**
	 * Creates a new TaskWorker
	 */
	CoreAsyncTaskWorker(CoreSchedulerThread master) {
		this(master, null);
	}
	
	/**
	 * Creates a new TaskWorker
	 * @param task
	 */
	CoreAsyncTaskWorker(CoreSchedulerThread master, CoreRegisteredTask task) {
		super("Scheduler-Worker-" + COUNT.getAndIncrement());
		this.task = task;
		this.master = master;
		setDaemon(true);
	}
	
	@Override
	public void run() {
		while (isRunning()) {
			final CoreRegisteredTask task = getTask();
			if (task != null)
				task.getTask().run();
			lastActiv = System.currentTimeMillis();
			awaitTask();
		}
		CoreRegisteredTask task = getTask();
		if (task != null) 
			task.getTask().notifiyShutdown();
	}
	
	public synchronized void setTask(CoreRegisteredTask task) {
			lastActiv = System.currentTimeMillis();
			boolean notify = false;
			if (task == null) notify = true;
			this.task = task;
			if (notify) notify();
	}
	
	public CoreRegisteredTask getTask() {
		return task;
	}
	
	/**
	 * 
	 * @return the time this Worker was last Active in milliseconds
	 */
	public long getLastActive() {
		return lastActiv;
	}
	
	public synchronized void shutdown() {
		running = false;
		if (task == null) 
			notify();
	}
	
	private synchronized void awaitTask() {
		CoreRegisteredTask task = master.fetchTask();
		if (task != null) {
			this.task = task;
			return;
		}
		master.restoreWorker(this);
		try {
			wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public boolean isRunning() {
		return running;
	}
	

}
