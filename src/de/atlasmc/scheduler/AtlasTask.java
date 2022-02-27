package de.atlasmc.scheduler;

import de.atlasmc.util.annotation.ThreadSafe;

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
	 * This method will be called if the scheduler is shutting down<br>
	 * It does nothing by default if you need it, simply override it<br>
	 */
	@ThreadSafe
	public void notifiyShutdown() {}
	
}
