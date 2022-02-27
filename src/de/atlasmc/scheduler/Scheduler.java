package de.atlasmc.scheduler;

import de.atlasmc.util.ConcurrentLinkedList.EntryPointer;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.annotation.ThreadSafe;

@ThreadSafe
public interface Scheduler {
	
	/**
	 * Runs a Task at the next tick
	 * @param task that should be run
	 */
	public void runSyncTask(AtlasTask task);
	
	/**
	 * Runs a Task at the next tick
	 * @param task that should be run
	 */
	public void runSyncTask(Runnable task);
	
	/**
	 * Runs a Task after a delay
	 * @param task that should be run
	 * @param delay in ticks
	 */
	public void runSyncTaskLater(AtlasTask task, long delay);
	
	/**
	 * Runs a Task after a delay at a fixed period of time with a max amount of repeats
	 * @param task that should be run
	 * @param delay in ticks
	 * @param period in ticks
	 * @param repeats number of repeats
	 */
	public void runSyncTaskFor(AtlasTask task, long delay, long period, long repeats);
	
	/**
	 * Runs a Task after a delay at a fixed period of time
	 * @param task that should be run
	 * @param delay in ticks
	 * @param period in ticks
	 */
	public void runSyncRepeatingTask(AtlasTask task, long delay, long period);
	
	/**
	 * Runs a Task at the next tick
	 * @param task that should be run
	 */
	public void runAsyncTask(AtlasTask task);
	
	/**
	 * Runs a Task after a delay
	 * @param task that should be run
	 * @param delay
	 */
	public void runAsyncTaskLater(AtlasTask task, long delay);
	
	/**
	 * Runs a Task after a delay at a fixed period of time with a max amount of repeats
	 * @param task that should be run
	 * @param delay
	 * @param period
	 * @param repeats
	 */
	public void runAsyncTaskFor(AtlasTask task, long delay, long period, long repeats);
	
	/**
	 * Runs a Task after a delay at a fixed period of time
	 * @param task that should be run
	 * @param delay
	 * @param period
	 */
	public void runAsyncRepeatingTask(AtlasTask task, long delay, long period);

	/**
	 * Runs the queued tasks for the next tick sync<br>
	 * Should only be called by e.g. a servers thread
	 */
	public void runNextTasks();
	
	/**
	 * Shuts this {@link Scheduler} and all children down
	 */
	public void shutdown();
	
	/**
	 * Returns whether or not this Scheduler has been shut down
	 * @return true if shut down
	 */
	public boolean isDead();
	
	/**
	 * Creates a new Scheduler with this Scheduler as parent
	 * @return new Scheduler
	 */
	@NotNull
	public Scheduler createScheduler();
	
	/**
	 * Adds a Scheduler as child and returns its pointer for later removal
	 * @param scheduler
	 * @return pointer
	 */
	public EntryPointer<Scheduler> addChild(Scheduler scheduler);

}
