package de.atlasmc;

import java.io.File;

import de.atlasmc.datarepository.DataRepositoryHandler;
import de.atlasmc.log.Log;
import de.atlasmc.plugin.Plugin;
import de.atlasmc.plugin.PluginManager;
import de.atlasmc.scheduler.Scheduler;
import de.atlasmc.tick.AtlasThread;
import de.atlasmc.util.Builder;
import de.atlasmc.util.secrets.SecretManager;

public final class AtlasBuilder implements Builder<Boolean> {

	private Scheduler scheduler;
	private Log logger;
	private File workDir;
	private PluginManager pluginManager;
	private SecretManager secrets;
	private DataRepositoryHandler dataHandler;
	private AtlasThread<Void> mainThread;
	private Plugin system;
	
	@Override
	public Boolean build() {
		Atlas.init(this);
		return Boolean.TRUE;
	}

	public Scheduler getScheduler() {
		return scheduler;
	}

	public AtlasBuilder setScheduler(Scheduler scheduler) {
		this.scheduler = scheduler;
		return this;
	}

	public Log getLogger() {
		return logger;
	}

	public AtlasBuilder setLogger(Log logger) {
		this.logger = logger;
		return this;
	}

	public File getWorkDir() {
		return workDir;
	}

	public AtlasBuilder setWorkDir(File workDir) {
		this.workDir = workDir;
		return this;
	}

	public PluginManager getPluginManager() {
		return pluginManager;
	}

	public AtlasBuilder setPluginManager(PluginManager pluginManager) {
		this.pluginManager = pluginManager;
		return this;
	}

	public SecretManager getSecretManager() {
		return secrets;
	}
	
	public AtlasBuilder setSecretManager(SecretManager secrets) {
		this.secrets = secrets;
		return this;
	}

	public DataRepositoryHandler getDataHandler() {
		return dataHandler;
	}

	public AtlasBuilder setDataHandler(DataRepositoryHandler dataHandler) {
		this.dataHandler = dataHandler;
		return this;
	}
	
	public AtlasThread<Void> getMainThread() {
		return mainThread;
	}
	
	public AtlasBuilder setMainThread(AtlasThread<Void> mainThread) {
		this.mainThread = mainThread;
		return this;
	}
	
	public Plugin getSystem() {
		return system;
	}
	
	public AtlasBuilder setSystem(Plugin system) {
		this.system = system;
		return this;
	}

	public static void initWorkDir(File workDir) {
		Atlas.initWorkDir(workDir);
	}

}
