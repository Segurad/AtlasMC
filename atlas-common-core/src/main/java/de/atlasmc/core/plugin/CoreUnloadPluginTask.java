package de.atlasmc.core.plugin;

import de.atlasmc.log.Log;
import de.atlasmc.plugin.Plugin;
import de.atlasmc.plugin.PluginException;
import de.atlasmc.util.concurrent.future.CompletableFuture;

final class CoreUnloadPluginTask implements Runnable {

	private final CorePluginManager manager;
	private final CoreRegisteredPlugin plugin;
	private final boolean force;
	private final CompletableFuture<Boolean> future;
	
	public CoreUnloadPluginTask(CorePluginManager manager, CompletableFuture<Boolean> future, CoreRegisteredPlugin plugin, boolean force) {
		this.manager = manager;
		this.plugin = plugin;
		this.force = force;
		this.future = future;
	}
	
	@Override
	public void run() {
		if (!force && plugin.isAlive()) {
			future.complete(false);
			return;
		}
		if (manager.getRegisteredPlugin(plugin.prototype.getName()) == plugin) {
			future.complete(true);
			return;
		}
		Plugin pl = plugin.prototype.getPlugin();
		Log log = manager.getLogger();
		try {
			pl.disable();
		} catch (Exception e) {
			PluginException ex = new PluginException("Error while disabling plugin!", e);
			log.error(ex);
			future.complete(false, ex);
		}
		try {
			pl.unload();
		} catch (Exception e) {
			PluginException ex = new PluginException("Error while unloading plugin!", e);
			log.error(ex);
			future.complete(false, ex);
		}
		manager.removePlugin(plugin);
		plugin.clearDependencies();
		plugin.clearDepending();
		plugin.clearLocks();
		plugin.setKeepLoaded(false);
		future.complete(true);
	}

}
