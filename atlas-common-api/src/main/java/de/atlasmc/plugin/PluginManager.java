package de.atlasmc.plugin;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import de.atlasmc.NamespacedKey;
import de.atlasmc.event.Event;
import de.atlasmc.event.EventPriority;
import de.atlasmc.event.FunctionalListener;
import de.atlasmc.event.Listener;
import de.atlasmc.util.annotation.Nullable;
import de.atlasmc.util.concurrent.future.Future;

public interface PluginManager {
	
	/**
	 * Loads and returns the Plugin represented by the file or null if unable to load.<br>
	 * If the given lock is null {@link Plugin#isKeepLoaded()} is set to true.
	 * @param file file of the Plugin
	 * @param lock to keep the Plugin alive
	 * @return Plugin or null if not successful
	 */
	Future<Plugin> loadPlugin(File file, Object lock);
	
	Future<Plugin> loadRepoPlugin(NamespacedKey entry, Object lock);
	
	default List<Future<Plugin>> loadPlugins(File directory) {
		return loadPlugins(directory, null);
	}
	
	/**
	 * Loads all Plugins. Should only be called on startup.
	 * @param directory of Plugin files
	 * @param lock to keep the Plugin alive
	 * @return List of Plugins that are successfully loaded
	 * @see #loadPlugins(File)
	 */
	List<Future<Plugin>> loadPlugins(File directory, Object lock);
	
	List<Future<Plugin>> loadPlugins(Collection<File> files, Object lock);
	
	List<Future<Plugin>> loadRepoPlugins(Collection<NamespacedKey> entries, Object lock);
	
	/**
	 * Unloads the given Plugin and removes it from this PluginManager.
	 * The future will be false if dependencies or locks still exist and force is false.
	 * @param plugin to unload
	 * @param force if the Plugin should be force unloaded
	 * @return true if success
	 */
	Future<Boolean> unloadPlugin(Plugin plugin, boolean force);
	
	/**
	 * Sets a lock to the Plugin to keep it alive.
	 * If a Plugin does have no remaining locks and is not {@link Plugin#isKeepLoaded()} the Plugin will be unloaded.
	 * The lock will not be used for synchronization purposes and keeped as {@link WeakReference}. 
	 * If the reference is no longer active the lock is released.
	 * @param plugin
	 * @param lock
	 */
	boolean lockPlugin(Plugin plugin, Object lock);
	
	boolean unlockPlugin(Plugin plugin, Object lock);
	
	boolean isLocked(Plugin plugin);
	
	/**
	 * Clears all locks for the given Plugin and returns the number of locks cleared.
	 * @param plugin
	 * @return cleared count
	 */
	int clearLocks(Plugin plugin);
	
	/**
	 * Returns the number of active locks.
	 * @param plugin
	 * @return lock count
	 */
	int lockCount(Plugin plugin);
	
	boolean isKeepLoaded(Plugin plugin);
	
	void setKeepLoaded(Plugin plugin, boolean keepLoaded);
	
	Collection<Plugin> getPlugins();
	
	@Nullable
	Plugin getPlugin(String name);
	
	Collection<PluginLoader> getLoaders();
	
	/**
	 * Adds a PluginLoader
	 * @param loader
	 * @return success
	 */
	boolean addLoader(PluginLoader loader);
	
	/**
	 * Removes a PluginLoader
	 * @param loader
	 * @return success
	 */
	boolean removeLoader(PluginLoader loader);

	/**
	 * Returns the number of Plugins registered on this PluginManager
	 * @return count
	 */
	int getPluginCount();
	
	void registerEvents(PluginHandle plugin, Listener listener);
	
	void registerEvents(PluginHandle plugin, Listener listener, Object... context);
	
	<E extends Event> void registerFunctionalListener(PluginHandle plugin, Class<E> eventClass, FunctionalListener<E> listener);
	
	<E extends Event> void registerFunctionalListener(PluginHandle plugin, Class<E> eventClass, FunctionalListener<E> listener,  Object... context);

	<E extends Event> void registerFunctionalListener(PluginHandle plugin, Class<E> eventClass, FunctionalListener<E> listener, boolean ignoreCancelled, EventPriority priority);

	<E extends Event> void registerFunctionalListener(PluginHandle plugin, Class<E> eventClass, FunctionalListener<E> listener, boolean ignoreCancelled, EventPriority priority, Object... context);	

	void removeEvents(PluginHandle handle);
	
	void callEvent(Event event);
	
	Set<NamespacedKey> getFeatures();
	
	boolean hasFeature(NamespacedKey feature);
	
	void addFeature(NamespacedKey feature);

	boolean removeFeature(NamespacedKey feature);
	
}
