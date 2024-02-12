package de.atlasmc.atlasnetwork;

import java.io.File;
import java.security.KeyPair;
import java.security.PublicKey;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import de.atlasmc.atlasnetwork.proxy.LocalProxy;
import de.atlasmc.atlasnetwork.server.LocalServer;
import de.atlasmc.atlasnetwork.server.ServerGroup;
import de.atlasmc.datarepository.DataRepositoryHandler;
import de.atlasmc.event.SyncThreadHolder;
import de.atlasmc.io.protocol.ProtocolAdapterHandler;
import de.atlasmc.log.Log;
import de.atlasmc.plugin.PluginManager;
import de.atlasmc.scheduler.Scheduler;
import de.atlasmc.util.TickingThread;
import de.atlasmc.util.concurrent.future.CompleteFuture;
import de.atlasmc.util.concurrent.future.Future;

public class LocalAtlasNode implements AtlasNode, SyncThreadHolder {
	
	private final ProtocolAdapterHandler adapterHandler;
	private final Scheduler scheduler;
	private final Log logger;
	private final File workdir;
	private final KeyPair keyPair;
	private final Map<UUID, LocalServer> servers;
	private final Map<UUID, LocalProxy> proxies;
	private final PluginManager pluginManager;
	private final TickingThread mainThread;
	private final DataRepositoryHandler dataHandler;
	private final UUID uuid;
	
	public LocalAtlasNode(UUID uuid, Log logger, Scheduler scheduler, File workdir, PluginManager pluginManager, TickingThread mainThread, KeyPair keyPair, DataRepositoryHandler dataHandler) {
		if (uuid == null)
			throw new IllegalArgumentException("UUID can not be null!");
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
		if (dataHandler == null)
			throw new IllegalArgumentException("Data handler can not be null!");
		this.uuid = uuid;
		this.dataHandler = dataHandler;
		this.adapterHandler = new ProtocolAdapterHandler();
		this.scheduler = scheduler;
		this.logger = logger;
		this.workdir = workdir;
		this.proxies = new ConcurrentHashMap<>();
		this.servers = new ConcurrentHashMap<>(5);
		this.pluginManager = pluginManager;
		this.mainThread = mainThread;
		this.keyPair = keyPair;
	}
	
	public Collection<LocalServer> getServers() {
		return servers.values();
	}

	public Collection<LocalProxy> getProxies() {
		return proxies.values();
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

	public Log getLogger() {
		return logger;
	}

	@Override
	public boolean isSync() {
		return Thread.currentThread() == mainThread;
	}

	public File getWorkdir() {
		return workdir;
	}
	
	public PluginManager getPluginManager() {
		return pluginManager;
	}
	
	public KeyPair getKeyPair() {
		return keyPair;
	}
	
	public DataRepositoryHandler getDataHandler() {
		return dataHandler;
	}

	public void registerProxy(LocalProxy proxy) {
		proxies.put(proxy.getUUID(), proxy);
	}

	@Override
	public Future<? extends LocalServer> getServer(UUID uuid) {
		return CompleteFuture.of(servers.get(uuid));
	}
	
	public LocalServer getLocalServer(UUID uuid) {
		return servers.get(uuid);
	}

	@Override
	public Future<? extends LocalProxy> getProxy(UUID uuid) {
		return CompleteFuture.of(proxies.get(uuid));
	}
	
	public LocalProxy getLocalProxy(UUID uuid) {
		return proxies.get(uuid);
	}

	@Override
	public Future<Collection<? extends ServerGroup>> getServerGroups() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PublicKey getPublicKey() {
		return keyPair.getPublic();
	}

	@Override
	public UUID getUUID() {
		return uuid;
	}

}
