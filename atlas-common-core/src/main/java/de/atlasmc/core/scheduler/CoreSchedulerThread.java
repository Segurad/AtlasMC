package de.atlasmc.core.scheduler;

import java.util.concurrent.ConcurrentLinkedQueue;

import de.atlasmc.log.Logging;
import de.atlasmc.tick.TickingThread;
import de.atlasmc.util.ConcurrentLinkedList;

public class CoreSchedulerThread extends TickingThread {
	
	private final CoreAtlasScheduler scheduler;
	private final ConcurrentLinkedQueue<CoreAsyncTaskWorker> workerQueue;
	private final ConcurrentLinkedList<CoreAsyncTaskWorker> fetchedWorkers;
	private final ConcurrentLinkedQueue<CoreRegisteredTask> tasks;
	private final int minWorkers;
	private final int workerMaxIdleTime;
	private final int asyncWorkerGCTime;
	private int gcTime;
	private volatile int workerCount;
	
	public CoreSchedulerThread(CoreAtlasScheduler scheduler, int minWorkers, int workerMaxIdleTime, int asyncWorkerGCTime) {
		super("Atlas-Scheduler-Deamon", 50, Logging.getLogger("Scheduler", "Atlas"), false);
		getLogger().sendToConsole(true);
		setDaemon(true);
		this.scheduler = scheduler;
		this.workerQueue = new ConcurrentLinkedQueue<>();
		this.fetchedWorkers = new ConcurrentLinkedList<>();
		this.tasks = new ConcurrentLinkedQueue<>();
		this.minWorkers = minWorkers;
		this.workerMaxIdleTime = workerMaxIdleTime;
		this.asyncWorkerGCTime = asyncWorkerGCTime;
	}

	/**
	 * This Method will be called by this Schedulers Worker to GC all AsyncWorkers that exceeded the workerMaxIdleTime
	 */
	private final void runAsyncWorkerGC() {
		if (workerQueue.isEmpty())
			return;
		if (workerCount <= minWorkers) 
			return;
		final long time = System.currentTimeMillis();
		for (CoreAsyncTaskWorker worker : workerQueue) {
			if (time - worker.getLastActive() < workerMaxIdleTime) 
				continue;
			worker.shutdown();
			synchronized (this) {
				workerCount--;
			}
		}
	}
	
	final void fetchWorker(CoreRegisteredTask task) {
		if (!scheduler.isDead()) 
			return;
		CoreAsyncTaskWorker worker = null;
		do { // find queued non dead worker
			worker = workerQueue.poll();
			if (!worker.isAlive())
				continue;
			fetchedWorkers.add(worker);
			worker.setTask(task);
			return;
		} while(worker != null);
		tasks.add(task); // queue task
	}
	
	final void restoreWorker(CoreAsyncTaskWorker worker) {
		workerQueue.add(worker);
	}
	
	@Override
	protected void tick(int tick) {
		gcTime++;
		scheduler.tickTasks(this);
		if (tasks.size() > 10) { // try to reduce queue load
			CoreRegisteredTask task = tasks.poll();
			if (task != null)
				synchronized (this) {
					CoreAsyncTaskWorker worker = new CoreAsyncTaskWorker(this, task);
					fetchedWorkers.add(worker);
					workerCount++;
					worker.start();
				}
		}
		if (gcTime < asyncWorkerGCTime)
			return;
		runAsyncWorkerGC();
		gcTime = 0;
	}
	
	@Override
	public void shutdown() {
		super.shutdown();
	}

	/**
	 * Returns a Queued async task
	 * @return task or null
	 */
	CoreRegisteredTask fetchTask() {
		CoreRegisteredTask task = tasks.poll();
		return task;
	}

}
