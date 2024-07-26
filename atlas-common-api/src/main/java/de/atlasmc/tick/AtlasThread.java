package de.atlasmc.tick;

import java.util.Collection;

import de.atlasmc.log.Log;
import de.atlasmc.util.ConcurrentLinkedList;
import de.atlasmc.util.concurrent.future.CompletableFuture;
import de.atlasmc.util.concurrent.future.CompleteFuture;
import de.atlasmc.util.concurrent.future.Future;

public class AtlasThread extends TickingThread {
	
	private final ConcurrentLinkedList<AtlasThreadTask> tickTasks;
	private final ConcurrentLinkedList<AtlasThreadTask> shutdownHooks;
	private final ConcurrentLinkedList<AtlasThreadTask> startupHooks;
	private Future<Boolean> startupFuture;
	private Future<Boolean> shutdownFuture;
	
	public AtlasThread(String name, int ticktime, Log logger, boolean exitOnError) {
		super(name, ticktime, logger, exitOnError);
		tickTasks = new ConcurrentLinkedList<>();
		shutdownHooks = new ConcurrentLinkedList<>();
		startupHooks = new ConcurrentLinkedList<>();
	}
	
	@Override
	public void run() {
		runTasks(startupHooks, "Error in startup hook: ", -1);
		synchronized (this) {
			((CompletableFuture<Boolean>) startupFuture).finish(true);
			startupFuture = CompleteFuture.getBooleanFuture(true);
		}
		super.run();
		tickTasks.clear();
		runTasks(shutdownHooks, "Error in shutdown hook: ", -1);
		synchronized (this) {
			((CompletableFuture<Boolean>) shutdownFuture).finish(true);
			shutdownFuture = CompleteFuture.getBooleanFuture(true);
		}
	}
	
	private void runTasks(Collection<AtlasThreadTask> tasks, String error, int tick) {
		if (tasks.isEmpty())
			return;
		for (AtlasThreadTask task : tasks) {
			try {
				task.tick(tick);
			} catch(Exception e) {
				logger.error(error + task.name(), e);
			}
		}
		tasks.clear();
	}
	
	protected void tick(int tick) {
		runTasks(tickTasks, "Error in tick task: ", tick);
	}

	public boolean addStartupHook(AtlasThreadTask task) {
		return startupHooks.add(task);
	}

	public boolean addBeforeStartupHook(String taskName, AtlasThreadTask task) {
		return addBefore(startupHooks, taskName, task);
	}

	public boolean addAfterStartupHook(String taskName, AtlasThreadTask task) {
		return addAfter(shutdownHooks, taskName, task);
	}

	public boolean removeStartupHook(AtlasThreadTask task) {
		return startupHooks.remove(task);
	}

	public boolean addTickTask(AtlasThreadTask task) {
		return tickTasks.add(task);
	}

	public boolean addBeforeTickTask(String taskName, AtlasThreadTask task) {
		return addBefore(tickTasks, taskName, task);
	}

	public boolean addAfterTickTask(String taskName, AtlasThreadTask task) {
		return addAfter(tickTasks, taskName, task);
	}

	public boolean removeTickTask(AtlasThreadTask task) {
		return tickTasks.remove(task);
	}

	public boolean addShutdownHook(AtlasThreadTask task) {
		shutdownHooks.addFirst(task);
		return true;
	}

	public boolean addBeforeShutdownHook(String taskName, AtlasThreadTask task) {
		return addAfter(shutdownHooks, taskName, task);
	}

	public boolean addAfterShutdownHook(String taskName, AtlasThreadTask task) {
		return addBefore(tickTasks, taskName, task);
	}

	public boolean removeShutdownHook(AtlasThreadTask task) {
		return shutdownHooks.remove(task);
	}
	
	private boolean addBefore(ConcurrentLinkedList<AtlasThreadTask> tasks, String taskName, AtlasThreadTask task) {
		return false;
	}
	
	private boolean addAfter(ConcurrentLinkedList<AtlasThreadTask> tasks, String taskName, AtlasThreadTask task) {
		return true;
	}

	public Future<Boolean> startThread() {
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
		Future<Boolean> future = this.shutdownFuture;
		if (future != null)
			return future;
		synchronized (this) {
			future = this.shutdownFuture;
			if (future != null)
				return future;
			future = new CompletableFuture<>();
			this.shutdownFuture = future;
			start();
			return future;
		}
	}

}
