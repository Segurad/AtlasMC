package de.atlasmc.scheduler;

final class AsyncTaskWorker extends Thread {
	
	private volatile RegisteredTask task;
	private volatile long lastActiv;
	private final AtlasScheduler scheduler;
	private volatile boolean running;
	
	/**
	 * Creates a new TaskWorker which will automatically start
	 */
	AsyncTaskWorker(AtlasScheduler scheduler) {
		this(scheduler, null);
	}
	
	/**
	 * Creates a new TaskWorker which will automatically start
	 * @param task
	 */
	AsyncTaskWorker(AtlasScheduler scheduler, RegisteredTask task) {
		this.task = task;
		this.scheduler = scheduler;
		start();
	}
	
	@Override
	public void run() {
		long time = 0;
		while (isRunning()) {
			RegisteredTask task = getTask();
			if (task != null) {
				time = System.currentTimeMillis(); // To Track the Time the Task needed for execution
				task.tick();
				if (task.isRunnable()) {
					task.getTask().run();
				}
				if (task.unregister()) { 
					setTask(null);
					lastActiv = System.currentTimeMillis();
					scheduler.restoreWorker(this);
					time = -1;
				} else
				time = System.currentTimeMillis() - time;
				if (time > 50) {
					time = 50 - (time % 50);
				} else time = 50 - time;
				try {
					if (time >= 0)
					Thread.sleep(time);
					else wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else {
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		RegisteredTask task = getTask();
		if (task != null) task.getTask().notifiyShutdown();
	}
	
	public void setTask(RegisteredTask task) {
			lastActiv = System.currentTimeMillis();
			boolean notify = false;
			if (task == null) notify = true;
			this.task = task;
			if (notify) notify();
	}
	
	public RegisteredTask getTask() {
		return task;
	}
	
	/**
	 * 
	 * @return the time this Worker was last Active in milliseconds
	 */
	public long getLastActive() {
		return lastActiv;
	}
	
	public void shutdown() {
		running = false;
		if (task == null) notify();
	}
	
	public boolean isRunning() {
		return running;
	}
	

}
