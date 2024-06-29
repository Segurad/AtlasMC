package de.atlascore.scheduler;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import de.atlasmc.plugin.Plugin;
import de.atlasmc.scheduler.AtlasTask;
import de.atlasmc.scheduler.Scheduler;
import de.atlasmc.util.ConcurrentLinkedList;
import de.atlasmc.util.ConcurrentLinkedList.LinkedListIterator;

public abstract class CoreAbstractScheduler implements Scheduler {

	protected final ConcurrentLinkedList<CoreRegisteredTask> asyncTasks;
	protected final ConcurrentLinkedList<CoreRegisteredTask> syncTasks;
	private final Set<Scheduler> children;
	protected final LinkedListIterator<CoreRegisteredTask> asyncIt;
	protected final LinkedListIterator<CoreRegisteredTask> syncIt;

	/**
	 * If this task has been shutdown
	 */
	private volatile boolean dead;
	
	public CoreAbstractScheduler() {
		this.syncTasks = new ConcurrentLinkedList<>();
		this.asyncTasks = new ConcurrentLinkedList<>();
		this.children = ConcurrentHashMap.newKeySet();
		this.asyncIt = asyncTasks.iterator();
		this.syncIt = syncTasks.iterator();
	}
	
	@Override
	public AtlasTask runSyncTask(Plugin plugin, Runnable task) {
		return runSyncTaskLater(plugin, task, 0);
	}

	@Override
	public AtlasTask runSyncTaskLater(Plugin plugin, Runnable task, long delay) {
		AtlasTask atask = asTask(task);
		addSyncTask(new CoreDelayedTask(plugin, atask, delay));
		return atask;
	}

	@Override
	public AtlasTask runSyncTaskFor(Plugin plugin, Runnable task, long delay, long period, long repeats) {
		AtlasTask atask = asTask(task);
		addSyncTask(new CoreCountedRepeatingTask(plugin, atask, delay, period, repeats));
		return atask;
	}

	@Override
	public AtlasTask runSyncRepeatingTask(Plugin plugin, Runnable task, long delay, long period) {
		AtlasTask atask = asTask(task);
		addSyncTask(new CoreRepeatingTask(plugin, atask, delay, period));
		return atask;
	}

	@Override
	public AtlasTask runAsyncTask(Plugin plugin, Runnable task) {
		AtlasTask atask = asTask(task);
		addAsyncTask(new CoreDelayedTask(plugin, atask, 0));
		return atask;
	}

	@Override
	public AtlasTask runAsyncTaskLater(Plugin plugin, Runnable task, long delay) {
		AtlasTask atask = asTask(task);
		addAsyncTask(new CoreDelayedTask(plugin, atask, delay));
		return atask;
	}

	@Override
	public AtlasTask runAsyncTaskFor(Plugin plugin, Runnable task, long delay, long period, long repeats) {
		AtlasTask atask = asTask(task);
		addAsyncTask(new CoreRepeatingTask(plugin, atask, delay, period));
		return atask;
	}

	@Override
	public AtlasTask runAsyncRepeatingTask(Plugin plugin, Runnable task, long delay, long period) {
		AtlasTask atask = asTask(task);
		addAsyncTask(new CoreRepeatingTask(plugin, atask, delay, period));
		return atask;
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
	
	@Override
	public synchronized void shutdown() {
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
	}
	
	@Override
	public void runNextTasks() {
		if (dead)
			return;
		for (CoreRegisteredTask task = syncIt.gotoHead(); task != null; syncIt.next()) {
			if (tickSyncTask(task))
				syncIt.remove();
		}
	}
	
	@Override
	public final boolean isDead() {
		return dead;
	}
	
	@Override
	public boolean addChild(Scheduler scheduler) {
		return children.add(scheduler);
	}
	
	@Override
	public boolean removeChild(Scheduler scheduler) {
		return children.remove(scheduler);
	}
	
	/**
	 * Ticks all async tasks and execute them if they are executable
	 * @param master
	 */
	protected void tickTasks(CoreSchedulerThread master) {
		if (dead)
			return;
		for (CoreRegisteredTask task = asyncIt.gotoHead(); task != null; asyncIt.next()) {
			if (tickAsyncTask(master, task))
				asyncIt.remove();
		}
		if (!children.isEmpty()) {
			for (Scheduler scheduler : children) {
				if (scheduler instanceof CoreAbstractScheduler)
					((CoreAbstractScheduler) scheduler).tickTasks(master);
			}
		}
	}
	
	/**
	 * Ticks a sync tasks and adds it to {@link #nextSyncTask} when executed on the next tick
	 * @param task
	 * @return true if task is dead
	 */
	private boolean tickSyncTask(CoreRegisteredTask task) {
		task.tick();
		if (task.isRunnable())
			task.getTask().run();
		return task.isDead();
	}
	
	/**
	 * Ticks a async task and executes it if possible
	 * @param master
	 * @param task
	 * @return true if dead
	 */
	private boolean tickAsyncTask(CoreSchedulerThread master, CoreRegisteredTask task) {
		task.tick();
		if (task.isRunnable())
			master.fetchWorker(task);
		return task.isDead();
	}
	
	private AtlasTask asTask(Runnable task) {
		if (task instanceof AtlasTask atlasTask) {
			return atlasTask;
		} else {
			return new CoreAtlasTaskWrapper(task);
		}
	}
	
	@Override
	public void removeTasks(Plugin plugin) {
		Iterator<CoreRegisteredTask> tasks = null;
		CoreRegisteredTask task = null;
		if (!asyncTasks.isEmpty()) {
			tasks = asyncTasks.iterator();
			while ((task = tasks.next()) != null) {
				tasks.remove();
				task.getTask().cancel();
			}
		}
		if (!syncTasks.isEmpty()) {
			tasks = syncTasks.iterator();
			task = null;
			while ((task = tasks.next()) != null) {
				tasks.remove();
				task.getTask().cancel();
			}
		}
		if (!children.isEmpty()) {
			for (Scheduler child : children)
				child.removeTasks(plugin);
		}
	}
	
}
