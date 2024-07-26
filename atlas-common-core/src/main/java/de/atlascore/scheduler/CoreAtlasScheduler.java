package de.atlascore.scheduler;

import de.atlasmc.scheduler.Scheduler;
import de.atlasmc.util.ThreadWatchdog;

public class CoreAtlasScheduler extends CoreAbstractScheduler {
	
	private CoreSchedulerThread thread;
	
	public CoreAtlasScheduler() {
		this(0, 6000, 600);
	}
	
	/**
	 * 
	 * @param parent
	 * @param minWorkers
	 * @param workerMaxIdleTime in millis
	 * @param asyncWorkerGCTime in ticks or -1 to disable
	 */
	public CoreAtlasScheduler(int minWorkers, int workerMaxIdleTime, int asyncWorkerGCTime) {
		super();
		thread = new CoreSchedulerThread(this, minWorkers, workerMaxIdleTime, asyncWorkerGCTime);
		thread.setDaemon(true);
		thread.start();
		ThreadWatchdog.watch(thread);
	}
	
	@Override
	public synchronized void shutdown() {
		if (thread == null)
			return;
		thread.shutdown();
		thread = null;
		super.shutdown();
	}

	@Override
	public Scheduler createScheduler() {
		return new CoreChildScheduler(this);
	}

}
