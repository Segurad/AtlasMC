package de.atlascore.scheduler;

import java.util.concurrent.ConcurrentLinkedQueue;

import de.atlasmc.scheduler.AtlasTask;
import de.atlasmc.scheduler.Scheduler;
import de.atlasmc.util.ConcurrentLinkedList;
import de.atlasmc.util.ConcurrentLinkedList.EntryPointer;
import de.atlasmc.util.ConcurrentLinkedList.LinkedListIterator;

public abstract class CoreAbstractScheduler implements Scheduler {

	protected final ConcurrentLinkedList<CoreRegisteredTask> asyncTasks;
	protected final ConcurrentLinkedList<CoreRegisteredTask> syncTasks;
	protected final ConcurrentLinkedQueue<Runnable> nextSyncTask;
	private final ConcurrentLinkedList<Scheduler> children;
	protected final LinkedListIterator<CoreRegisteredTask> asyncIt;
	protected final LinkedListIterator<CoreRegisteredTask> syncIt;

	/**
	 * If this task has been shutdown
	 */
	private volatile boolean dead;
	
	public CoreAbstractScheduler() {
		this.nextSyncTask = new ConcurrentLinkedQueue<>();
		this.syncTasks = new ConcurrentLinkedList<>();
		this.asyncTasks = new ConcurrentLinkedList<>();
		this.children = new ConcurrentLinkedList<>();
		this.asyncIt = asyncTasks.iterator();
		this.syncIt = syncTasks.iterator();
	}
	
	@Override
	public void runSyncTask(AtlasTask task) {
		addSyncTaskNext(task);
	}
	
	@Override
	public void runSyncTask(Runnable task) {
		addSyncTaskNext(task);
	}

	@Override
	public void runSyncTaskLater(AtlasTask task, long delay) {
		addSyncTask(new CoreDelayedTask(task, delay));
	}

	@Override
	public void runSyncTaskFor(AtlasTask task, long delay, long period, long repeats) {
		addSyncTask(new CoreCountedRepeatingTask(task, delay, period, repeats));
	}

	@Override
	public void runSyncRepeatingTask(AtlasTask task, long delay, long period) {
		addSyncTask(new CoreRepeatingTask(task, delay, period));
	}

	@Override
	public void runAsyncTask(AtlasTask task) {
		addAsyncTask(new CoreDelayedTask(task, 0));
	}

	@Override
	public void runAsyncTaskLater(AtlasTask task, long delay) {
		addAsyncTask(new CoreDelayedTask(task, delay));
	}

	@Override
	public void runAsyncTaskFor(AtlasTask task, long delay, long period, long repeats) {
		addAsyncTask(new CoreRepeatingTask(task, delay, period));
	}

	@Override
	public void runAsyncRepeatingTask(AtlasTask task, long delay, long period) {
		addAsyncTask(new CoreRepeatingTask(task, delay, period));
	}
	
	/**
	 * Adds a task for sync scheduling to this Scheduler
	 * @param task
	 */
	protected void addSyncTask(CoreRegisteredTask task) {
		syncTasks.add(task);
	}
	
	/**
	 * Adds a task for async scheduling to this Scheduler
	 * @param task
	 */
	protected void addAsyncTask(CoreRegisteredTask task) {
		asyncTasks.add(task);
	}

	/**
	 * Adds a task for the next sync execution to this Scheduler
	 * @param task
	 */
	protected void addSyncTaskNext(Runnable task) {
		nextSyncTask.add(task);
	}
	
	@Override
	public void shutdown() {
		if (dead)
			return;
		dead = true;
		for (Scheduler scd : children) {
			scd.shutdown();
		}
		children.clear();
		for (CoreRegisteredTask task : asyncTasks) {
			task.getTask().notifiyShutdown();
		}
		asyncTasks.clear();
		for (CoreRegisteredTask task : syncTasks) {
			task.getTask().notifiyShutdown();
		}
		syncTasks.clear();
		nextSyncTask.clear();
	}
	
	@Override
	public void runNextTasks() {
		if (dead)
		for (Runnable task = nextSyncTask.poll(); task != null; task = nextSyncTask.poll()) {
			task.run();
		}
	}
	
	@Override
	public final boolean isDead() {
		return dead;
	}
	
	@Override
	public EntryPointer<Scheduler> addChild(Scheduler scheduler) {
		return children.addWithPointer(scheduler);
	}
	
	protected void scheduleWorkers(CoreSchedulerThread master) {
		if (dead)
			return;
		for (CoreRegisteredTask task = syncIt.gotoHead(); task != null; syncIt.next()) {
			if (dead)
				return;
			if (scheduleSyncTask(task))
				syncIt.remove();
		}
		for (CoreRegisteredTask task = asyncIt.gotoHead(); task != null; asyncIt.next()) {
			if (dead)
				return;
			if (scheduleAsyncTask(master, task))
				asyncIt.remove();
		}
	}
	
	protected boolean scheduleSyncTask(CoreRegisteredTask task) {
		task.tick();
		if (task.isRunnable())
			nextSyncTask.add(task.getTask());
		return task.isDead();
	}
	
	protected boolean scheduleAsyncTask(CoreSchedulerThread master, CoreRegisteredTask task) {
		task.tick();
		if (task.isRunnable())
			master.fetchWorker(task);
		return task.isDead();
	}
	
}
