package de.atlasmc.scheduler;

import de.atlasmc.plugin.PluginHandle;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.annotation.ThreadSafe;

@ThreadSafe
public interface Scheduler {
	
	/**
	 * Runs a Task at the next tick
	 * @param plugin the Plugin to run the task for
	 * @param task that should be run
	 * @return task
	 */
	AtlasTask runSyncTask(PluginHandle plugin, Runnable task);
	
	/**
	 * Runs a Task after a delay
	 * @param plugin the Plugin to run the task for
	 * @param task that should be run
	 * @param delay in ticks
	 * @return task
	 */
	AtlasTask runSyncTaskLater(PluginHandle plugin, Runnable task, long delay);
	
	/**
	 * Runs a Task after a delay at a fixed period of time with a max amount of repeats
	 * @param plugin the Plugin to run the task for
	 * @param task that should be run
	 * @param delay in ticks
	 * @param period in ticks
	 * @param repeats number of repeats
	 * @return task
	 */
	AtlasTask runSyncTaskFor(PluginHandle plugin, Runnable task, long delay, long period, long repeats);
	
	/**
	 * Runs a Task after a delay at a fixed period of time
	 * @param plugin the Plugin to run the task for
	 * @param task that should be run
	 * @param delay in ticks
	 * @param period in ticks
	 * @return task
	 */
	AtlasTask runSyncRepeatingTask(PluginHandle plugin, Runnable task, long delay, long period);
	
	/**
	 * Runs a Task at the next tick
	 * @param plugin the Plugin to run the task for
	 * @param task that should be run
	 * @return task
	 */
	AtlasTask runAsyncTask(PluginHandle plugin, Runnable task);
	
	/**
	 * Runs a Task after a delay
	 * @param plugin the Plugin to run the task for
	 * @param task that should be run
	 * @param delay
	 * @return task
	 */
	AtlasTask runAsyncTaskLater(PluginHandle plugin, Runnable task, long delay);
	
	/**
	 * Runs a Task after a delay at a fixed period of time with a max amount of repeats
	 * @param plugin the Plugin to run the task for
	 * @param task that should be run
	 * @param delay
	 * @param period
	 * @param repeats
	 * @return task
	 */
	AtlasTask runAsyncTaskFor(PluginHandle plugin, Runnable task, long delay, long period, long repeats);
	
	/**
	 * Runs a Task after a delay at a fixed period of time
	 * @param plugin the Plugin to run the task for
	 * @param task that should be run
	 * @param delay
	 * @param period
	 * @return task
	 */
	AtlasTask runAsyncRepeatingTask(PluginHandle plugin, Runnable task, long delay, long period);

	/**
	 * Runs the queued tasks for the next tick sync<br>
	 * Should only be called by e.g. a servers thread
	 */
	void runNextTasks();
	
	/**
	 * Shuts this {@link Scheduler} and all children down
	 */
	void shutdown();
	
	/**
	 * Returns whether or not this Scheduler has been shut down
	 * @return true if shut down
	 */
	boolean isDead();
	
	/**
	 * Creates a new Scheduler with this Scheduler as parent
	 * @return new Scheduler
	 */
	@NotNull
	Scheduler createScheduler();
	
	/**
	 * Adds a Scheduler as child and returns its pointer for later removal
	 * @param scheduler
	 * @return pointer
	 */
	boolean addChild(Scheduler scheduler);
	
	boolean removeChild(Scheduler scheduler);
	
	/**
	 * Removes all task of the given Plugin
	 * @param plugin
	 */
	void removeTasks(PluginHandle plugin);

}
