package de.atlasmc.scheduler;

import java.util.Vector;
import java.util.concurrent.ConcurrentLinkedQueue;

import de.atlasmc.atlasnetwork.server.LocalServer;

public class AtlasScheduler implements Scheduler {

	private final LocalServer server;
	private final SyncTaskWorker worker;
	private final ConcurrentLinkedQueue<AsyncTaskWorker> workerQueue;
	private final Vector<AsyncTaskWorker> fetchedWorkers;
	private final int minWorkers;
	private final long workerMaxIdleTime;
	
	public AtlasScheduler(LocalServer server) {
		this(server, 2, 6000, 12000);
	}
	
	/**
	 * 
	 * @param server
	 * @param minWorkers
	 * @param workerMaxIdleTime in milliseconds
	 * @param asyncWorkerGCTime in Ticks or -1 to disable
	 */
	public AtlasScheduler(LocalServer server, int minWorkers, int workerMaxIdleTime, int asyncWorkerGCTime) {
		workerQueue = new ConcurrentLinkedQueue<AsyncTaskWorker>();
		this.fetchedWorkers = new Vector<AsyncTaskWorker>();
		this.server = server;
		this.minWorkers = minWorkers;
		this.workerMaxIdleTime = workerMaxIdleTime;
		worker = new SyncTaskWorker(this, server.getIdentifier(), asyncWorkerGCTime);
	}
	
	public void runNextTasks() {
		if (!server.isServerThread()) throw new RuntimeException("Async SyncScheduler call!");
		worker.runNextTasksSync();
	}

	@Override
	public void runSyncTask(AtlasTask task) {
		worker.addTask(task);
	}
	
	@Override
	public void runSyncTask(Runnable task) {
		worker.addTask(task);
	}

	@Override
	public void runSyncTaskLater(AtlasTask task, long delay) {
		worker.addTask(new DelayedTask(task, delay));
	}

	@Override
	public void runSyncTaskFor(AtlasTask task, long delay, long period, long repeats) {
		worker.addTask(new CountedRepeatingTask(task, delay, period, repeats));
	}

	@Override
	public void runSyncRepeatingTask(AtlasTask task, long delay, long period) {
		worker.addTask(new RepeatingTask(task, delay, period));
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
	
	/**
	 * This Method will be called by this Schedulers Worker to GC all AsyncWorkers that exceeded the workerMaxIdleTime
	 */
	final void runAsyncWorkerGC() {
		int workers = workerQueue.size() + fetchedWorkers.size();
		if (workers == minWorkers) return;
		if (workers < minWorkers) {
			for (int i = workers; i < minWorkers; i++) {
				workerQueue.add(new AsyncTaskWorker(this));
			}
		} else {
			long time = System.currentTimeMillis();
			for (AsyncTaskWorker worker : workerQueue) {
				if (time - worker.getLastActive() < workerMaxIdleTime) continue;
				worker.shutdown();
			}
		}
	}

	final void restoreWorker(AsyncTaskWorker worker) {
		fetchedWorkers.remove(worker);
		workerQueue.add(worker);
	}
	
	public void shutdown() {
		worker.shutdown();
		for (AsyncTaskWorker worker : fetchedWorkers) {
			worker.shutdown();
		}
		while (!workerQueue.isEmpty()) {
			workerQueue.poll().shutdown();
		}
	}
	
	public boolean isRunning() {
		return worker.isRunning();
	}
}
