package de.atlasmc.scheduler;

abstract class RegisteredTask {
	
	private final AtlasTask task;

	public RegisteredTask(AtlasTask task) {
		this.task = task;
	}
	
	public abstract void tick();
	
	public boolean unregister() {
		return task.isCancelled();
	}
	public abstract boolean isRunnable();
	
	public AtlasTask getTask() {
		return task;
	}

}
