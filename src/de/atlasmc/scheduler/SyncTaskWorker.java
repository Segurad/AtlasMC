package de.atlasmc.scheduler;

import java.util.Vector;
import java.util.concurrent.ConcurrentLinkedQueue;

public class SyncTaskWorker extends Thread {
	
	private final Vector<RegisteredTask> tasks;
	private final ConcurrentLinkedQueue<Runnable> nextTasks;
	private volatile boolean running;
	private final int asyncWorkerGCTime;
	private final AtlasScheduler scheduler;
	
	public SyncTaskWorker(AtlasScheduler scheduler, String name, int asyncWorkerGCTime) {
		super("SyncTaskWorker: " + name);
		this.scheduler = scheduler;
		tasks = new Vector<RegisteredTask>();
		this.asyncWorkerGCTime = asyncWorkerGCTime;
		nextTasks = new ConcurrentLinkedQueue<Runnable>();
	}
	
	@Override
	public void run() {
		while (running) {
			int asyncWorkerGCIn = asyncWorkerGCTime;
			long time = System.currentTimeMillis();
			if (!tasks.isEmpty()) {
				for (RegisteredTask task : tasks) {
					task.tick();
					if (task.isRunnable()) {
						nextTasks.add(task.getTask());
					}
					if (task.unregister()) tasks.remove(task);
				}
				if (asyncWorkerGCTime > 0)
				asyncWorkerGCIn--;
				if (asyncWorkerGCIn <= 0) {
					scheduler.runAsyncWorkerGC();
				}
				time = System.currentTimeMillis() - time;
				if (time > 50) {
					time = 50 - (time % 50);
				} else time = 50 - time;
			} else time = 50;
			try {
				Thread.sleep(time);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		for (RegisteredTask task : tasks) {
			task.getTask().notifiyShutdown();
		}
		tasks.clear();
		while (!nextTasks.isEmpty()) {
			Runnable r = nextTasks.poll();
			if (r instanceof AtlasTask) {
				((AtlasTask) r).notifiyShutdown();
			}
		}
	}

	public void runNextTasksSync() {
		while(!nextTasks.isEmpty()) {
			nextTasks.poll().run();
		}
	}

	public void addTask(AtlasTask task) {
		if (running)
		nextTasks.add(task);
	}

	public void addTask(Runnable task) {
		if (running)
		nextTasks.add(task);
	}

	public void addTask(RegisteredTask task) {
		if (running)
		tasks.add(task);
	}

	public void shutdown() {
		running = false;
		for (RegisteredTask task : tasks) {
			task.getTask().notifiyShutdown();
		}
	}

	public boolean isRunning() {
		return running;
	}

}
