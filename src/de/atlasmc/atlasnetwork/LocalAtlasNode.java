package de.atlasmc.atlasnetwork;

import java.io.File;
import java.security.KeyPair;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;

import de.atlasmc.atlasnetwork.proxy.LocalProxy;
import de.atlasmc.atlasnetwork.server.LocalServer;
import de.atlasmc.event.SyncThreadHolder;
import de.atlasmc.io.protocol.ProtocolAdapterHandler;
import de.atlasmc.plugin.PluginManager;
import de.atlasmc.scheduler.Scheduler;
import de.atlasmc.util.TickingThread;

public class LocalAtlasNode implements AtlasNode, SyncThreadHolder {
	
	private final ProtocolAdapterHandler adapterHandler;
	private final Scheduler scheduler;
	private final Logger logger;
	private final File workdir;
	private final KeyPair keyPair;
	private List<LocalServer> servers;
	private List<LocalProxy> proxies;
	private final PluginManager pluginManager;
	private final TickingThread mainThread;
	
	public LocalAtlasNode(Logger logger, Scheduler scheduler, File workdir, PluginManager pluginManager, TickingThread mainThread, KeyPair keyPair) {
		if (logger == null)
			throw new IllegalArgumentException("Logger can not be null!");
		if (scheduler == null)
			throw new IllegalArgumentException("Scheduler can not be null!");
		if (workdir == null)
			throw new IllegalArgumentException("Work dir can not be null!");
		if (pluginManager == null)
			throw new IllegalArgumentException("PluginManager can not be null!");
		if (mainThread == null)
			throw new IllegalArgumentException("Main thread can not be null!");
		if (keyPair == null)
			throw new IllegalArgumentException("Key pair can not be null!");
		this.adapterHandler = new ProtocolAdapterHandler();
		this.scheduler = scheduler;
		this.logger = logger;
		this.workdir = workdir;
		this.proxies = new ArrayList<>();
		this.servers = new ArrayList<>();
		this.pluginManager = pluginManager;
		this.mainThread = mainThread;
		this.keyPair = keyPair;
	}
	
	@Override
	public List<LocalServer> getServers() {
		return servers;
	}

	@Override
	public List<LocalProxy> getProxies() {
		return proxies;
	}

	public ProtocolAdapterHandler getProtocolAdapterHandler() {
		return adapterHandler;
	}

	@Override
	public boolean isOnline() {
		return true;
	}

	public Scheduler getScheduler() {
		return scheduler;
	}

	public Logger getLogger() {
		return logger;
	}

	@Override
	public boolean isSync() {
		return Thread.currentThread() == mainThread;
	}

	public File getWorkDir() {
		return workdir;
	}
	
	public PluginManager getPluginManager() {
		return pluginManager;
	}
	
	public KeyPair getKeyPair() {
		return keyPair;
	}

}
