package de.atlasmc.tick;

import de.atlasmc.log.Log;
import de.atlasmc.util.concurrent.future.CompletableFuture;
import de.atlasmc.util.concurrent.future.CompleteFuture;
import de.atlasmc.util.concurrent.future.Future;

public class AtlasThread<T> extends TickingThread {
	
	private final AtlasThreadTaskManager<T> tickTasks;
	private final AtlasThreadTaskManager<T> shutdownTasks;
	private final AtlasThreadTaskManager<T> startupTasks;
	private final T context;
	private Future<Boolean> startupFuture;
	private Future<Boolean> shutdownFuture;
	
	public AtlasThread(String name, int ticktime, Log logger, boolean exitOnError,  T context) {
		this(name, ticktime, logger, exitOnError, context, new AtlasThreadTaskManager<>(), new AtlasThreadTaskManager<>(), new AtlasThreadTaskManager<>());
	}
	
	public AtlasThread(String name, int ticktime, Log logger, boolean exitOnError, T context,  
			AtlasThreadTaskManager<T> startupTasks, AtlasThreadTaskManager<T> tickTasks, AtlasThreadTaskManager<T> shutdownTasks) {
		super(name, ticktime, logger, exitOnError);
		//if (context == null)
		//	throw new IllegalArgumentException("Context can not be null!");
		if (startupTasks == null)
			throw new IllegalArgumentException("Startup tasks can not be null!");
		if (tickTasks == null)
			throw new IllegalArgumentException("Tick tasks can not be null!");
		if (shutdownTasks == null)
			throw new IllegalArgumentException("Shutdown tasks can not be null!");
		this.context = context;
		this.tickTasks = tickTasks;
		this.shutdownTasks = shutdownTasks;
		this.startupTasks = startupTasks;
	}
	
	@Override
	public void run() {
		running = true;
		runTasks(startupTasks, "Error in startup task: ", -1);
		if (running) {
			synchronized (this) {
				((CompletableFuture<Boolean>) startupFuture).complete(true);
				startupFuture = CompleteFuture.getBooleanFuture(true);
			}
			super.run();
		}
		runTasks(shutdownTasks, "Error in shutdown task: ", -1);
		synchronized (this) {
			((CompletableFuture<Boolean>) shutdownFuture).complete(true);
			shutdownFuture = CompleteFuture.getBooleanFuture(true);
		}
	}
	
	private void runTasks(AtlasThreadTaskManager<T> tasks, String error, int tick) {
		final AtlasThreadTask<T>[] fastTasks = tasks.fastTasks;
		if (fastTasks.length == 0)
			return;
		for (AtlasThreadTask<T> task : fastTasks) {
			try {
				task.run(context, tick);
			} catch (Exception e) {
				logger.error(error + tasks.getTaskName(task), e);
			}
		}
	}
	
	public AtlasThreadTaskManager<T> getTickTasks() {
		return tickTasks;
	}
	
	public AtlasThreadTaskManager<T> getStartupTasks() {
		return startupTasks;
	}
	
	public AtlasThreadTaskManager<T> getShutdownTasks() {
		return shutdownTasks;
	}
	
	protected void tick(int tick) {
		runTasks(tickTasks, "Error in tick task: ", tick);
	}

	public Future<Boolean> startThread() {
		if (getState() == State.TERMINATED)
			return CompleteFuture.getBooleanFuture(false);
		Future<Boolean> future = this.startupFuture;
		if (future != null)
			return future;
		synchronized (this) {
			future = this.startupFuture;
			if (future != null)
				return future;
			future = new CompletableFuture<>();
			this.startupFuture = future;
			start();
			return future;
		}
	}

	public Future<Boolean> stopThread() {
		if (getState() == State.NEW)
			return CompleteFuture.getBooleanFuture(false);
		Future<Boolean> future = this.shutdownFuture;
		if (future != null)
			return future;
		synchronized (this) {
			future = this.shutdownFuture;
			if (future != null)
				return future;
			future = new CompletableFuture<>();
			this.shutdownFuture = future;
			shutdown();
			return future;
		}
	}

}
