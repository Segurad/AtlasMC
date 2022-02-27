package de.atlascore.scheduler;

import de.atlasmc.scheduler.Scheduler;

public class CoreAtlasScheduler extends CoreAbstractScheduler {
	
	private final CoreSchedulerThread thread;
	
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
	}
	
	@Override
	public void shutdown() {
		thread.stopThread();
		super.shutdown();
	}

	@Override
	public Scheduler createScheduler() {
		return new CoreChildScheduler(this);
	}

}
