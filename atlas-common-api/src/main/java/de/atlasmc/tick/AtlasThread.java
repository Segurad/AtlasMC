package de.atlasmc.tick;

import java.util.Objects;

import de.atlasmc.log.Log;
import de.atlasmc.util.concurrent.future.CompletableFuture;
import de.atlasmc.util.concurrent.future.CompleteFuture;
import de.atlasmc.util.concurrent.future.Future;

public class AtlasThread<T> extends TickingThread {
	
	private final AtlasThreadTaskManager<T> tickTasks;
	private final AtlasThreadTaskManager<T> shutdownTasks;
	private final AtlasThreadTaskManager<T> startupTasks;
	private final T context;
	private volatile Future<Boolean> startupFuture;
	private volatile Future<Boolean> shutdownFuture;
	
	public AtlasThread(String name, int ticktime, Log logger, boolean exitOnError,  T context) {
		this(name, ticktime, logger, exitOnError, context, new AtlasThreadTaskManager<>(), new AtlasThreadTaskManager<>(), new AtlasThreadTaskManager<>());
	}
	
	public AtlasThread(String name, int ticktime, Log logger, boolean exitOnError, T context,  
			AtlasThreadTaskManager<T> startupTasks, AtlasThreadTaskManager<T> tickTasks, AtlasThreadTaskManager<T> shutdownTasks) {
		super(name, ticktime, logger, exitOnError);
		//if (context == null)
		//	throw new IllegalArgumentException("Context can not be null!");
		this.context = context;
		this.tickTasks = Objects.requireNonNull(tickTasks, "tick tasks");
		this.shutdownTasks = Objects.requireNonNull(shutdownTasks, "shutdown tasks");
		this.startupTasks = Objects.requireNonNull(startupTasks, "startup tasks");
	}
	
	@Override
	public void run() {
		running = true;
		startupTasks.runTasks(this, "Error in startup task: ", -1);
		if (running) {
			synchronized (this) {
				((CompletableFuture<Boolean>) startupFuture).complete(true);
				startupFuture = CompleteFuture.getBooleanFuture(true);
			}
			super.run();
		}
		shutdownTasks.runTasks(this, "Error in shutdown task: ", -1);
		synchronized (this) {
			((CompletableFuture<Boolean>) shutdownFuture).complete(true);
			shutdownFuture = CompleteFuture.getBooleanFuture(true);
		}
	}
	
	public T getContext() {
		return context;
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
	
	@Override
	protected void tick(int tick) {
		tickTasks.runTasks(this, "Error in tick task: ", tick);
	}

	public synchronized Future<Boolean> startThread() {
		if (getState() == State.TERMINATED)
			return CompleteFuture.getBooleanFuture(false);
		var future = this.startupFuture;
		if (future != null)
			return future;
		future = new CompletableFuture<>();
		this.startupFuture = future;
		start();
		return future;
	}

	public synchronized Future<Boolean> stopThread() {
		if (getState() == State.NEW)
			return CompleteFuture.getBooleanFuture(false);
		var future = this.shutdownFuture;
		if (future != null)
			return future;
		future = new CompletableFuture<>();
		this.shutdownFuture = future;
		shutdown();
		return future;
	}

}
