package de.atlascore.plugin;

import java.util.ArrayList;
import java.util.Collection;

import de.atlasmc.Atlas;
import de.atlasmc.plugin.Plugin;
import de.atlasmc.plugin.PreparedPlugin;
import de.atlasmc.scheduler.AtlasTask;
import de.atlasmc.util.concurrent.future.CommulativeFuture;
import de.atlasmc.util.concurrent.future.CompletableFuture;
import de.atlasmc.util.concurrent.future.Future;

public class CorePluginLoaderTask extends AtlasTask {

	private final CorePluginManager pmanager;
	private final CompletableFuture<Plugin> future;
	private final PreparedPlugin plugin;
	private final boolean enable;
	private volatile boolean scheduled;
	private volatile boolean ready;
	
	public CorePluginLoaderTask(CorePluginManager pmanager, PreparedPlugin plugin, boolean enable) {
		this.pmanager = pmanager;
		this.plugin = plugin;
		this.future = new CompletableFuture<>();
		this.enable = enable;
	}
	
	public void prepare() {
		if (!plugin.hasDependencies() && !plugin.hasSoftDependencies()) {
			ready = true;
			return;
		}
		ArrayList<Future<Collection<Future<Plugin>>>> cumFuture = new ArrayList<>(2);
		if (plugin.hasDependencies())
			cumFuture.add(new CommulativeFuture<>(plugin.getDependencies()));
		if (plugin.hasSoftDependencies())
			cumFuture.add(new CommulativeFuture<>(plugin.getSoftDependencies()));
		new CommulativeFuture<>(cumFuture).setListener((future) -> {
			ready = true;
			schedule();
		});
	}
	
	public void schedule() {
		if (scheduled)
			return;
		synchronized (this) {
			if (scheduled)
				return;
			scheduled = true;
			Atlas.getScheduler().runAsyncTask(CorePluginManager.SYSTEM, this);
		}
	}
	
	public Future<Plugin> getFuture() {
		return future;
	}
	
	public PreparedPlugin getPlugin() {
		return plugin;
	}
	
	public boolean isReady() {
		return ready;
	}
	
	public boolean isFinished() {
		return future.isDone();
	}
	
	@Override
	public void run() {
		if (isFinished())
			return;
		try {
			Plugin plugin = pmanager.loadPreparedPlugin(this, enable);
			future.finish(plugin);
		} catch(Exception e) {
			future.finish(null, e);
		}
	}

}
