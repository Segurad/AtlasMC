package de.atlascore.scheduler;

final class CoreAsyncTaskWorker extends Thread {
	
	private volatile CoreRegisteredTask task;
	private volatile long lastActiv;
	private final CoreSchedulerThread master;
	private volatile boolean running;
	
	/**
	 * Creates a new TaskWorker which will automatically start
	 */
	CoreAsyncTaskWorker(CoreSchedulerThread master) {
		this(master, null);
	}
	
	/**
	 * Creates a new TaskWorker which will automatically start
	 * @param task
	 */
	CoreAsyncTaskWorker(CoreSchedulerThread master, CoreRegisteredTask task) {
		this.task = task;
		this.master = master;
		start();
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
		if (task == null) notify();
	}
	
	private synchronized void awaitTask() {
		try {
			master.restoreWorker(this);
			wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public boolean isRunning() {
		return running;
	}
	

}
