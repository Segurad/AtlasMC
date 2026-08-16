package de.atlasmc.core.scheduler;

import java.util.Arrays;
import java.util.Objects;

import de.atlasmc.plugin.Plugin;
import de.atlasmc.plugin.PluginHandle;
import de.atlasmc.scheduler.AtlasTask;
import de.atlasmc.scheduler.Scheduler;
import de.atlasmc.util.ConcurrentLinkedList;
import de.atlasmc.util.ConcurrentLinkedList.LinkedListIterator;
import de.atlasmc.util.annotation.NotNull;

public abstract class CoreAbstractScheduler implements Scheduler {

	protected final ConcurrentLinkedList<CoreRegisteredTask> asyncTasks;
	protected final ConcurrentLinkedList<CoreRegisteredTask> syncTasks;
	private volatile Scheduler[] children;
	protected final LinkedListIterator<CoreRegisteredTask> asyncIt;
	protected final LinkedListIterator<CoreRegisteredTask> syncIt;

	/**
	 * If this task has been shutdown
	 */
	private volatile boolean dead;
	
	public CoreAbstractScheduler() {
		this.syncTasks = new ConcurrentLinkedList<>();
		this.asyncTasks = new ConcurrentLinkedList<>();
		this.asyncIt = asyncTasks.iterator();
		this.syncIt = syncTasks.iterator();
	}
	
	@Override
	public AtlasTask runSyncTask(PluginHandle plugin, Runnable task) {
		return runSyncTaskLater(plugin, task, 0);
	}

	@Override
	public AtlasTask runSyncTaskLater(PluginHandle plugin, Runnable task, long delay) {
		AtlasTask atask = asTask(task);
		addSyncTask(new CoreDelayedTask(plugin, atask, delay));
		return atask;
	}

	@Override
	public AtlasTask runSyncTaskFor(PluginHandle plugin, Runnable task, long delay, long period, long repeats) {
		AtlasTask atask = asTask(task);
		addSyncTask(new CoreCountedRepeatingTask(plugin, atask, delay, period, repeats));
		return atask;
	}

	@Override
	public AtlasTask runSyncRepeatingTask(PluginHandle plugin, Runnable task, long delay, long period) {
		AtlasTask atask = asTask(task);
		addSyncTask(new CoreRepeatingTask(plugin, atask, delay, period));
		return atask;
	}

	@Override
	public AtlasTask runAsyncTask(PluginHandle plugin, Runnable task) {
		AtlasTask atask = asTask(task);
		addAsyncTask(new CoreDelayedTask(plugin, atask, 0));
		return atask;
	}

	@Override
	public AtlasTask runAsyncTaskLater(PluginHandle plugin, Runnable task, long delay) {
		AtlasTask atask = asTask(task);
		addAsyncTask(new CoreDelayedTask(plugin, atask, delay));
		return atask;
	}

	@Override
	public AtlasTask runAsyncTaskFor(PluginHandle plugin, Runnable task, long delay, long period, long repeats) {
		AtlasTask atask = asTask(task);
		addAsyncTask(new CoreRepeatingTask(plugin, atask, delay, period));
		return atask;
	}

	@Override
	public AtlasTask runAsyncRepeatingTask(PluginHandle plugin, Runnable task, long delay, long period) {
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
		var children = this.children;
		if (children != null) {
			for (Scheduler scd : children) {
				scd.shutdown();
			}
			this.children = null;
		}
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
		if (dead || syncTasks.isEmpty())
			return;
		for (CoreRegisteredTask task = syncIt.gotoHead(); syncIt.hasNext(); task = syncIt.next()) {
			if (tickSyncTask(task))
				syncIt.remove();
		}
	}
	
	@Override
	public final boolean isDead() {
		return dead;
	}
	
	@Override
	public synchronized boolean addChild(Scheduler scheduler) {
		if (scheduler.isDead())
			throw new IllegalArgumentException("Child is dead!");
		var children = this.children;
		if (children == null) {
			this.children = new Scheduler[] { scheduler };
			return true;
		}
		for (var child : children) {
			if (child == scheduler)
				return false;
		}
		children = Arrays.copyOf(children, children.length + 1);
		children[children.length - 1] = scheduler;
		this.children = children;
		return true;
	}
	
	@Override
	public synchronized boolean removeChild(Scheduler scheduler) {
		Objects.requireNonNull(scheduler);
		var children = this.children;
		final int length = children.length;
		for (int i = 0; i < length; i++) {
			var child = children[i];
			if (child == scheduler) {
				var newLength = length - 1;
				if (newLength == 0) {
					this.children = null;
					return true;
				}
				var newChildren = Arrays.copyOf(children, length - 1);
				if (i < length - 1)
					newChildren[i] = children[length - 1];
				this.children = newChildren;
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Ticks all async tasks and execute them if they are executable
	 * @param master
	 */
	protected void tickTasks(CoreSchedulerThread master) {
		if (dead)
			return;
		if (!asyncTasks.isEmpty()) {
			for (CoreRegisteredTask task = asyncIt.gotoHead(); asyncIt.hasNext(); task = asyncIt.next()) {
				if (tickAsyncTask(master, task))
					asyncIt.remove();
			}
		}
		var children = this.children;
		if (children != null) {
			for (Scheduler scheduler : children) {
				if (scheduler instanceof CoreAbstractScheduler child)
					child.tickTasks(master);
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
	
	@NotNull
	private AtlasTask asTask(Runnable task) {
		if (task instanceof AtlasTask atlasTask) {
			return atlasTask;
		} else {
			return new CoreAtlasTaskWrapper(task);
		}
	}
	
	@Override
	public void removeAllTasks(Plugin plugin) {
		removeTasks(asyncTasks, plugin, true);
		removeTasks(syncTasks, plugin, true);
		var children = this.children;
		if (children != null) {
			for (Scheduler child : children)
				child.removeAllTasks(plugin);
		}
	}
	
	@Override
	public void removeTasks(PluginHandle plugin) {
		removeTasks(asyncTasks, plugin, false);
		removeTasks(syncTasks, plugin, false);
		var children = this.children;
		if (children != null) {
			for (Scheduler child : children)
				child.removeTasks(plugin);
		}
	}
	
	@Override
	public Scheduler createScheduler() {
		return new CoreChildScheduler(this);
	}
	
	private void removeTasks(ConcurrentLinkedList<CoreRegisteredTask> tasks, PluginHandle plugin, boolean byPlugin) {
		if (tasks.isEmpty())
			return;
		var it = tasks.iterator();
		while (it.hasNext()) {
			var task = it.next();
			var pl = task.getPlugin();
			if (pl == plugin || (byPlugin && pl.getPlugin() == plugin)) {
				it.remove();
				task.getTask().cancel();
			}
		}
	}
	
}
