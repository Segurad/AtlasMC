package de.atlasmc.scheduler;

public interface Scheduler {
	
	/**
	 * Runs a Task at the next tick
	 * @param task
	 */
	public void runSyncTask(AtlasTask task);
	
	public void runSyncTask(Runnable task);
	
	/**
	 * Runs a Task after a delay
	 * @param task
	 * @param delay
	 */
	public void runSyncTaskLater(AtlasTask task, long delay);
	
	/**
	 * Runs a Task after a delay at a fixed period of time with a max amount of repeats
	 * @param task
	 * @param delay
	 * @param period
	 * @param repeats
	 */
	public void runSyncTaskFor(AtlasTask task, long delay, long period, long repeats);
	
	/**
	 * Runs a Task after a delay at a fixed period of time
	 * @param task
	 * @param delay
	 * @param period
	 */
	public void runSyncRepeatingTask(AtlasTask task, long delay, long period);
	
	/**
	 * Runs a Task at the next tick
	 * @param task
	 */
	public void runAsyncTask(AtlasTask task);
	
	/**
	 * Runs a Task after a delay
	 * @param task
	 * @param delay
	 */
	public void runAsyncTaskLater(AtlasTask task, long delay);
	
	/**
	 * Runs a Task after a delay at a fixed period of time with a max amount of repeats
	 * @param task
	 * @param delay
	 * @param period
	 * @param repeats
	 */
	public void runAsyncTaskFor(AtlasTask task, long delay, long period, long repeats);
	
	/**
	 * Runs a Task after a delay at a fixed period of time
	 * @param task
	 * @param delay
	 * @param period
	 */
	public void runAsyncRepeatingTask(AtlasTask task, long delay, long period);

	/**
	 * Runs the queued tasks for the next tick
	 */
	public void runNextTasks();

}
