package de.atlascore.scheduler;

import java.util.concurrent.ConcurrentLinkedQueue;

import de.atlasmc.util.ConcurrentLinkedList;
import de.atlasmc.util.TickingThread;

public class CoreSchedulerThread extends TickingThread {

	private final CoreMasterScheduler scheduler;
	private final ConcurrentLinkedQueue<CoreAsyncTaskWorker> workerQueue;
	private final ConcurrentLinkedList<CoreAsyncTaskWorker> fetchedWorkers;
	private final int minWorkers;
	private final int workerMaxIdleTime;
	private final int asyncWorkerGCTime;
	private int gcTime;
	
	public CoreSchedulerThread(CoreMasterScheduler scheduler, int minWorkers, int workerMaxIdleTime, int asyncWorkerGCTime) {
		super("AtlasSchedulerWorker", 50);
		this.scheduler = scheduler;
		this.workerQueue = new ConcurrentLinkedQueue<CoreAsyncTaskWorker>();
		this.fetchedWorkers = new ConcurrentLinkedList<CoreAsyncTaskWorker>();
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
		int workers = workerQueue.size() + fetchedWorkers.size();
		if (workers <= minWorkers) 
			return;
		long time = System.currentTimeMillis();
		for (CoreAsyncTaskWorker worker : workerQueue) {
			if (time - worker.getLastActive() < workerMaxIdleTime) 
				continue;
			worker.shutdown();
		}
	}
	
	final void fetchWorker(CoreRegisteredTask task) {
		if (!scheduler.isDead()) 
			return;
		if (workerQueue.isEmpty()) {
			fetchedWorkers.add(new CoreAsyncTaskWorker(this, task));
		} else while(true) {
			CoreAsyncTaskWorker worker = workerQueue.poll();
			if (worker.isAlive()) {
				worker.setTask(task);
				fetchedWorkers.add(worker);
				break;
			}
		}
	}
	
	final void restoreWorker(CoreAsyncTaskWorker worker) {
		workerQueue.add(worker);
	}
	
	@Override
	protected void tick() {
		gcTime++;
		scheduler.tickTasks(this);
		if (gcTime < asyncWorkerGCTime)
			return;
		runAsyncWorkerGC();
		gcTime = 0;
	}

}
