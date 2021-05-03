package de.atlasmc.scheduler;

final class AsyncTaskWorker extends Thread {
	
	private RegisteredTask task;
	private long lastActiv, time;
	private final AtlasScheduler scheduler;
	private boolean running;
	
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
		while (isRunning()) {
			if (getTask() != null) {
				time = System.currentTimeMillis();
				task.tick();
				if (task.isRunnable()) {
					task.getTask().run();
				}
				if (task.unregister()) { 
					setTask(null);
					setLastActive(System.currentTimeMillis());
					scheduler.restoreWorker(this);
					time = 50;
				} else
				time = System.currentTimeMillis() - time;
				if (time > 50) {
					time = 50 - (time % 50);
				} else time = 50 - time;
			} else {
				if (System.currentTimeMillis() - getLastActive() > scheduler.getWorkerMaxIdleTime() && scheduler.canWorkerDie(this)) break;
				time = 50;
			}
			try {
				Thread.sleep(time);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		RegisteredTask task = getTask();
		if (task != null) task.getTask().notifiyShutdown();
	}
	
	public void setTask(RegisteredTask task) {
		synchronized (this) {
			setLastActive(0);
			this.task = task;
		}
	}
	
	public RegisteredTask getTask() {
		synchronized (this) {
			return task;
		}
	}
	
	public long getLastActive() {
		synchronized (this) {
			return lastActiv;
		}
	}
	
	private void setLastActive(long time) {
		synchronized (this) {
			lastActiv = time;
		}
	}
	
	public void shutdown() {
		synchronized (this) {
			running = false;
		}
	}
	
	public boolean isRunning() {
		synchronized (this) {
			return running;
		}
	}
	

}
