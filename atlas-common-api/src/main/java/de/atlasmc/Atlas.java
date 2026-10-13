package de.atlasmc;

import java.io.File;

import de.atlasmc.datarepository.DataRepositoryHandler;
import de.atlasmc.log.Log;
import de.atlasmc.plugin.Plugin;
import de.atlasmc.plugin.PluginManager;
import de.atlasmc.scheduler.Scheduler;
import de.atlasmc.tick.AtlasThread;
import de.atlasmc.util.annotation.InternalAPI;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.secrets.SecretManager;

public final class Atlas {
	
	private static Scheduler scheduler;
	private static Log logger;
	private static File workDir;
	private static PluginManager pluginManager;
	private static SecretManager secrets;
	private static DataRepositoryHandler dataHandler;
	private static AtlasThread<Void> mainThread;
	private static Plugin system;
	private static File tempDir;
	
	private Atlas() {
		// not required
	}
	
	static void initWorkDir(File workDir) {
		synchronized (Atlas.class) {
			if (Atlas.workDir != null)
				throw new IllegalStateException("Atlas already initialized!");
			Atlas.workDir = workDir;
			Atlas.tempDir = new File(workDir, "temp/");
		}
	}
	
	static void init(AtlasBuilder builder) {
		synchronized (Atlas.class) {
			if (logger != null)
				throw new IllegalStateException("Atlas already initialized!");
			scheduler = builder.getScheduler();
			logger = builder.getLogger();
			pluginManager = builder.getPluginManager();
			secrets = builder.getSecretManager();
			dataHandler = builder.getDataHandler();
			mainThread = builder.getMainThread();
			system = builder.getSystem();
		}
	}

	@NotNull
	public static Scheduler getScheduler() {
		return scheduler;
	}

	@NotNull
	public static Log getLogger() {
		return logger;
	}

	/**
	 * Working directory of Atlas
	 * @return directory
	 */
	@NotNull
	public static File getWorkdir() {
		return workDir;
	}
	
	/**
	 * Temp directory of Atlas
	 * @return temp directory
	 */
	@NotNull
	public static File getTempDir() {
		return tempDir;
	}
	
	@NotNull
	public static SecretManager getSecretManager() {
		return secrets;
	}
	
	@NotNull
	public static DataRepositoryHandler getDataHandler() {
		return dataHandler;
	}
	
	@NotNull
	public static PluginManager getPluginManager() {
		return pluginManager;
	}
	
	public static boolean isMainThread() {
		return Thread.currentThread() == mainThread;
	}
	
	public static AtlasThread<Void> getMainThread() {
		return mainThread;
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
