package de.atlasmc.scheduler;

import java.util.Vector;
import java.util.concurrent.ConcurrentLinkedQueue;

import de.atlasmc.atlasnetwork.server.LocalServer;

public class AtlasScheduler implements Scheduler {

	private final LocalServer server;
	private final ConcurrentLinkedQueue<AtlasTask> nextTasks;
	private final Vector<RegisteredTask> tasks;
	private final Thread worker;
	private final ConcurrentLinkedQueue<AsyncTaskWorker> workerQueue;
	private final Vector<AsyncTaskWorker> fetchedWorkers;
	private final int minWorkers;
	private final long workerMaxIdleTime;
	private boolean running;
	
	public AtlasScheduler(LocalServer server) {
		this(server, 2, 5000);
	}
	
	public AtlasScheduler(LocalServer server, int minWorkers, int workerMaxIdleTime) {
		workerQueue = new ConcurrentLinkedQueue<AsyncTaskWorker>();
		this.nextTasks = new ConcurrentLinkedQueue<AtlasTask>();
		this.tasks = new Vector<RegisteredTask>();
		this.fetchedWorkers = new Vector<AsyncTaskWorker>();
		this.server = server;
		this.minWorkers = minWorkers;
		this.workerMaxIdleTime = workerMaxIdleTime;
		worker = new Thread(() -> {
			while (isRunning()) {
				long time = System.currentTimeMillis();
				if (!tasks.isEmpty()) {
					for (RegisteredTask task : tasks) {
						task.tick();
						if (task.isRunnable()) {
							nextTasks.add(task.getTask());
						}
						if (task.unregister()) tasks.remove(task);
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
				nextTasks.poll().notifiyShutdown();
			}
			for (AsyncTaskWorker worker : fetchedWorkers) {
				worker.shutdown();
			}
			while (!workerQueue.isEmpty()) {
				workerQueue.poll().shutdown();
			}
		}, "SchedulerThread: " + server.getServerID());
		worker.start();
	}
	
	public void runNextTasks() {
		if (!server.isServerThread()) throw new RuntimeException("Async SyncScheduler call!");
		while(!nextTasks.isEmpty()) {
			nextTasks.poll().run();
		}
	}

	@Override
	public void runSyncTask(AtlasTask task) {
		nextTasks.add(task);
	}

	@Override
	public void runSyncTaskLater(AtlasTask task, long delay) {
		tasks.add(new DelayedTask(task, delay));
	}

	@Override
	public void runSyncTaskFor(AtlasTask task, long delay, long period, long repeats) {
		tasks.add(new CountedRepeatingTask(task, delay, period, repeats));
	}

	@Override
	public void runSyncRepeatingTask(AtlasTask task, long delay, long period) {
		tasks.add(new RepeatingTask(task, delay, period));
	}

	@Override
	public void runAsyncTask(AtlasTask task) {
		fetchWorker(new DelayedTask(task, 0));
	}

	@Override
	public void runAsyncTaskLater(AtlasTask task, long delay) {
		fetchWorker(new DelayedTask(task, delay));
	}

	@Override
	public void runAsyncTaskFor(AtlasTask task, long delay, long period, long repeats) {
		fetchWorker(new RepeatingTask(task, delay, period));
	}

	@Override
	public void runAsyncRepeatingTask(AtlasTask task, long delay, long period) {
		fetchWorker(new RepeatingTask(task, delay, period));
	}
	
	final void fetchWorker(RegisteredTask task) {
		if (!isRunning()) return;
		if (workerQueue.isEmpty()) {
			fetchedWorkers.add(new AsyncTaskWorker(this, task));
		} else while(true) {
			AsyncTaskWorker worker = workerQueue.poll();
			if (worker.isAlive()) {
				worker.setTask(task);
				fetchedWorkers.add(worker);
				break;
			}
		}
	}

	final void restoreWorker(AsyncTaskWorker worker) {
		fetchedWorkers.remove(worker);
		workerQueue.add(worker);
	}
	
	final long getWorkerMaxIdleTime() {
		return workerMaxIdleTime;
	}

	final boolean canWorkerDie(AsyncTaskWorker worker) {
		if (workerQueue.size() > minWorkers) {
			workerQueue.remove(worker);
			return true;
		} else return false;
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
