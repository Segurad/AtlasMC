package de.atlascore.plugin;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import de.atlasmc.NamespacedKey;
import de.atlasmc.plugin.PrototypePlugin;

final class CoreRegisteredPlugin {
	
	public final PrototypePlugin prototype;
	public final NamespacedKey repoEntry;
	private final CorePluginManager manager;
	private final Set<CoreRegisteredPlugin> dependencies;
	private final Set<CoreRegisteredPlugin> depending;
	private final List<CorePluginLock> locks;
	private volatile boolean keepLoaded;

	public CoreRegisteredPlugin(CorePluginManager manager, PrototypePlugin prototype, NamespacedKey repoEntry) {
		if (manager == null)
			throw new IllegalArgumentException("Manager can not be null!");
		if (prototype == null)
			throw new IllegalArgumentException("Prototype can not be null!");
		this.manager = manager;
		this.prototype = prototype;
		this.dependencies = ConcurrentHashMap.newKeySet();
		this.depending = ConcurrentHashMap.newKeySet();
		this.locks = new CopyOnWriteArrayList<>();
		this.repoEntry = repoEntry;
	}
	
	public void addDependency(CoreRegisteredPlugin plugin) {
		dependencies.add(plugin);
		plugin.depending.add(plugin);
	}
	
	public void removeDependency(CoreRegisteredPlugin plugin) {
		if (!dependencies.remove(plugin))
			return;
		if (plugin.depending.remove(plugin))
			plugin.checkPluginDeath();
	}
	
	public void clearDependencies() {
		for (CoreRegisteredPlugin plugin : this.dependencies) {
			removeDependency(plugin);
		}
	}

	public void clearDepending() {
		for (CoreRegisteredPlugin plugin : this.depending) {
			plugin.removeDependency(this);
		}
		checkPluginDeath();
	}

	public synchronized boolean lock(Object lock) {
		final int size = locks.size();
		for (int i = 0; i < size; i++) {
			CorePluginLock pluginLock = locks.get(i);
			if (pluginLock.refersTo(lock))
				return true;
		}
		locks.add(new CorePluginLock(this, lock, manager.lockQueue));
		checkPluginDeath();
		return true;
	}

	public synchronized boolean unlock(Object lock) {
		final int size = locks.size();
		boolean result = false;
		for (int i = 0; i < size; i++) {
			CorePluginLock pluginLock = locks.get(i);
			if (!pluginLock.refersTo(lock))
				continue;
			locks.remove(i);
			pluginLock.clear();
			result = true;
		}
		checkPluginDeath();
		return result;
	}
	
	/**
	 * Called by the lock monitor thread when a reference has died
	 * @param lock
	 */
	synchronized void clearLock(CorePluginLock lock) {
		locks.remove(lock);
		checkPluginDeath();
	}
	
	public boolean hasLocks() {
		return locks.isEmpty();
	}
	
	public boolean hasDependingPlugins() {
		return depending.isEmpty();
	}
	
	public boolean isAlive() {
		if (keepLoaded)
			return true;
		if (!locks.isEmpty())
			return true;
		if (!depending.isEmpty())
			return true;
		return false;
	}
	
	private void checkPluginDeath() {
		if (isAlive())
			return;
		manager.tryUnloadPlugin(this);
	}

	public synchronized int clearLocks() {
		final int size = locks.size();
		locks.clear();
		checkPluginDeath();
		return size;
	}

	public boolean isLocked() {
		return !locks.isEmpty();
	}

	public int lockCount() {
		return locks.size();
	}
	
	/**
	 * Whether or not this plugin will be keeped loaded without a depending server
	 * @return true if keep loaded
	 */
	public boolean isKeepLoaded() {
		return keepLoaded;
	}
	
	public void setKeepLoaded(boolean keeploaded) {
		this.keepLoaded = keeploaded;
		if (!keeploaded)
			checkPluginDeath();
	}
	
}
