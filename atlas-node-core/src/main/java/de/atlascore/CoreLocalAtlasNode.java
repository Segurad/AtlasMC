package de.atlascore;

import java.io.File;
import java.security.KeyPair;
import java.security.PublicKey;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import de.atlascore.plugin.CoreNodeBuilder;
import de.atlasmc.LocalAtlasNode;
import de.atlasmc.atlasnetwork.NodePlayer;
import de.atlasmc.atlasnetwork.server.ServerGroup;
import de.atlasmc.datarepository.DataRepositoryHandler;
import de.atlasmc.io.protocol.ProtocolAdapterHandler;
import de.atlasmc.log.Log;
import de.atlasmc.plugin.PluginManager;
import de.atlasmc.proxy.LocalProxy;
import de.atlasmc.scheduler.Scheduler;
import de.atlasmc.server.NodeServer;
import de.atlasmc.server.NodeServerManager;
import de.atlasmc.util.TickingThread;
import de.atlasmc.util.concurrent.future.CompleteFuture;
import de.atlasmc.util.concurrent.future.Future;

public class CoreLocalAtlasNode implements LocalAtlasNode {
	
	private final ProtocolAdapterHandler adapterHandler;
	private final Scheduler scheduler;
	private final Log logger;
	private final File workdir;
	private final KeyPair keyPair;
	private final Map<UUID, LocalProxy> proxies;
	private final PluginManager pluginManager;
	private final TickingThread mainThread;
	private final DataRepositoryHandler dataHandler;
	private final NodeServerManager smanager;
	private final UUID uuid;
	private NodeStatus status;
	
	public CoreLocalAtlasNode(CoreNodeBuilder builder) {
		this.uuid = builder.getUUID();
		this.dataHandler = builder.getDataHandler();
		this.adapterHandler = new ProtocolAdapterHandler();
		this.scheduler = builder.getScheduler();
		this.logger = builder.getLogger();
		this.workdir = builder.getWorkdir();
		this.proxies = new ConcurrentHashMap<>();
		this.pluginManager = builder.getPluginManager();
		this.mainThread = builder.getMainThread();
		this.keyPair = builder.getKeyPair();
		this.smanager = builder.getServerManager();
		this.status = NodeStatus.STARTING;
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
		if (smanager == null)
			throw new IllegalArgumentException("ServerManager can not be null!");
	}
	
	@Override
	public NodeServerManager getServerManager() {
		return smanager;
	}
	
	@Override
	public Collection<LocalProxy> getProxies() {
		return proxies.values();
	}

	@Override
	public ProtocolAdapterHandler getProtocolAdapterHandler() {
		return adapterHandler;
	}

	@Override
	public NodeStatus getStatus() {
		return status;
	}

	@Override
	public Scheduler getScheduler() {
		return scheduler;
	}
	
	@Override
	public Log getLogger() {
		return logger;
	}

	@Override
	public boolean isSync() {
		return Thread.currentThread() == mainThread;
	}

	@Override
	public File getWorkdir() {
		return workdir;
	}
	
	@Override
	public PluginManager getPluginManager() {
		return pluginManager;
	}
	
	@Override
	public KeyPair getKeyPair() {
		return keyPair;
	}
	
	@Override
	public DataRepositoryHandler getDataHandler() {
		return dataHandler;
	}

	@Override
	public void registerProxy(LocalProxy proxy) {
		proxies.put(proxy.getUUID(), proxy);
	}

	@Override
	public Future<? extends NodeServer> getServer(UUID uuid) {
		return CompleteFuture.of(smanager.getServer(uuid));
	}
	
	@Override
	public NodeServer getLocalServer(UUID uuid) {
		return smanager.getServer(uuid);
	}

	@Override
	public Future<? extends LocalProxy> getProxy(UUID uuid) {
		return CompleteFuture.of(proxies.get(uuid));
	}
	
	@Override
	public LocalProxy getLocalProxy(UUID uuid) {
		return proxies.get(uuid);
	}

	@Override
	public Future<Collection<? extends ServerGroup>> getServerGroups() {
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

	@Override
	public NodePlayer getLocalPlayer(String name) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public NodePlayer getLocalPlayer(UUID name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<NodeServer> getServers() {
		return smanager.getServers();
	}

	@Override
	public void setStatus(NodeStatus status) {
		this.status = status;
	}

}
