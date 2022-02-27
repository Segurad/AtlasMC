package de.atlascore.scheduler;

import de.atlasmc.scheduler.AtlasTask;

abstract class CoreRegisteredTask {
	
	private final AtlasTask task;

	public CoreRegisteredTask(AtlasTask task) {
		this.task = task;
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
