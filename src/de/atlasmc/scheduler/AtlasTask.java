package de.atlasmc.scheduler;

public abstract class AtlasTask implements Runnable {
	
	private volatile boolean cancelled;
	
	public abstract void run();
	
	public final boolean isCancelled() {
		return cancelled;
	}
	
	public final void cancel() {
		cancelled = true;
	}
	
	/**
	 * This method will be called if the scheduler is shutting down
	 * It does nothing by default if you need it, simply override it
	 * May be called twice if it is a sync task which would be called on the next tick
	 */
	public void notifiyShutdown() {}
	
}
