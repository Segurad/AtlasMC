package de.atlascore.scheduler;

import de.atlasmc.plugin.Plugin;
import de.atlasmc.scheduler.AtlasTask;

abstract class CoreRegisteredTask {
	
	private final AtlasTask task;
	private final Plugin plugin;

	public CoreRegisteredTask(Plugin plugin, AtlasTask task) {
		if (plugin == null)
			throw new IllegalArgumentException("Plugin can not be null!");
		if (task == null)
			throw new IllegalArgumentException("Task can not be null!");
		this.task = task;
		this.plugin = plugin;
	}
	
	public Plugin getPlugin() {
		return plugin;
	}
	
	public abstract void tick();
	
	/**
	 * Returns whether or not this task is Dead
	 * @return true if dead
	 */
	public boolean isDead() {
		return task.isCancelled();
	}
	public abstract boolean isRunnable();
	
	public AtlasTask getTask() {
		return task;
	}

}
