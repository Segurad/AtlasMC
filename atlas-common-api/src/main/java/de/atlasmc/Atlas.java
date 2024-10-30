package de.atlasmc;

import java.io.File;
import java.security.KeyPair;

import de.atlasmc.datarepository.DataRepositoryHandler;
import de.atlasmc.event.SyncThreadHolder;
import de.atlasmc.log.Log;
import de.atlasmc.plugin.Plugin;
import de.atlasmc.plugin.PluginManager;
import de.atlasmc.scheduler.Scheduler;
import de.atlasmc.tick.AtlasThread;
import de.atlasmc.util.annotation.InternalAPI;

public class Atlas implements SyncThreadHolder {

	public static final String FULL_VERSION = "atlasmc-dev";
	
	private static Scheduler scheduler;
	private static Log logger;
	private static File workDir;
	private static PluginManager pluginManager;
	private static KeyPair keyPair;
	private static DataRepositoryHandler dataHandler;
	private static Atlas threadHolder;
	private static AtlasThread mainThread;
	private static Plugin system;
	
	protected Atlas() {}
	
	static void initWorkDir(File workDir) {
		synchronized (Atlas.class) {
			if (Atlas.workDir != null)
				throw new IllegalStateException("Atlas already initialized!");
			Atlas.workDir = workDir;
		}
	}
	
	static void init(AtlasBuilder builder) {
		synchronized (Atlas.class) {
			if (threadHolder != null)
				throw new IllegalStateException("Atlas already initialized!");
			scheduler = builder.getScheduler();
			logger = builder.getLogger();
			pluginManager = builder.getPluginManager();
			keyPair = builder.getKeyPair();
			dataHandler = builder.getDataHandler();
			mainThread = builder.getMainThread();
			system = builder.getSystem();
			threadHolder = new Atlas();
		}
	}

	public static Scheduler getScheduler() {
		return scheduler;
	}

	public static Log getLogger() {
		return logger;
	}

	public static File getWorkdir() {
		return workDir;
	}
	
	public static KeyPair getKeyPair() {
		return keyPair;
	}
	
	public static DataRepositoryHandler getDataHandler() {
		return dataHandler;
	}
	
	public static PluginManager getPluginManager() {
		return pluginManager;
	}
	
	public static boolean isMainThread() {
		return Thread.currentThread() == mainThread;
	}
	
	public static Atlas getThreadHolder() {
		return threadHolder;
	}
	
	public static AtlasThread getMainThread() {
		return mainThread;
	}

	@Override
	public boolean isSync() {
		return Thread.currentThread() == mainThread;
	}
	
	/**
	 * Returns a plugin instance representing atlas.
	 * Do not use this!!!
	 * @return plugin
	 */
	@InternalAPI
	public static Plugin getSystem() {
		return system;
	}
	
}
