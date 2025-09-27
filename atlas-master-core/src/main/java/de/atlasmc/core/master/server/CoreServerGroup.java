package de.atlasmc.core.master.server;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import de.atlasmc.NamespacedKey;
import de.atlasmc.master.node.AtlasNode;
import de.atlasmc.master.server.Server;
import de.atlasmc.master.server.ServerGroup;
import de.atlasmc.master.server.ServerGroupBuilder;
import de.atlasmc.network.server.ServerConfig;
import de.atlasmc.tick.Tickable;

public class CoreServerGroup implements Tickable, ServerGroup  {
	
	private final String name;
	private final ServerConfig config;
	private final int maxServers;
	private final int maxNonFullServers;
	private final int minServers;
	private final int minNonFullServers;
	private final int newServerDelay;
	private final float newServerOnUserLoad;
	private volatile boolean maintenance;
	
	// node selector
	private float memoryUtilisation;
	private long memoryThreshold;
	private NamespacedKey deploymentMethod;
	private boolean internal;
	
	private int maxPlayer;
	private int playerCount;
	private int serverFull;
	
	private int ticksUnsatisfied;
	private int timeout;
	
	private CoreServerManager manager;
	
	private final Map<UUID, CoreServer> servers;
	
	public CoreServerGroup(CoreServerManager manager, ServerGroupBuilder builder) {
		this.manager = manager;
		this.deploymentMethod = builder.getDeploymentMethod();
		this.memoryThreshold = builder.getMemoryThreshold();
		this.memoryUtilisation = builder.getMemoryUtilisation();
		this.internal = builder.isInternal();
		this.config = builder.getServerConfig().clone();
		this.maxServers = builder.getMaxServers();
		this.maxNonFullServers = builder.getMaxNonFullServers();
		this.minServers = builder.getMinServers();
		this.minNonFullServers = builder.getMinNonFullServers();
		this.newServerDelay = builder.getNewServerDelay();
		this.newServerOnUserLoad = builder.getNewServerOnUserLoad();
		this.maintenance = builder.isMaintenance();
		this.name = builder.getName();
		servers = new ConcurrentHashMap<UUID, CoreServer>();
	}
	
	// runtime

	@Override
	public int getPlayerCount() {
		return playerCount;
	}

	@Override
	public Server getServer(UUID uuid) {
		if (uuid == null)
			throw new IllegalArgumentException("UUID can not be null!");
		Server server = servers.get(uuid);
		return server;
	}
	
	@Override
	public int getCurrentPlayerCapacity() {
		return maxPlayer;
	}
	
	@Override
	public NamespacedKey getDeploymentMethod() {
		return deploymentMethod;
	}
	
	@Override
	public long getMemoryThreshold() {
		return memoryThreshold;
	}
	
	@Override
	public float getMemoryUtilisation() {
		return memoryUtilisation;
	}
	
	@Override
	public boolean isInternal() {
		return internal;
	}
	
	@Override
	public boolean isUnsatisfied() {
		if (timeout > 0)
			return false;
		return ticksUnsatisfied > newServerDelay ||
				servers.size() < minServers;
	}
	
	@Override
	public int getFullServerCount() {
		return serverFull;
	}

	@Override
	public void tick() {
		if (timeout > 0) {
			timeout--;
		} else if (internalIsUnsatisfied()) {
			ticksUnsatisfied++;
		} else {
			ticksUnsatisfied = 0;
		}
	}
	
	protected boolean internalIsUnsatisfied() {
		int serverCount = servers.size();
		if (maxServers > 0 && serverCount == maxServers)
			return false; // no demand because no more servers may be deployed
		int serverNonFull = serverCount-serverFull;
		if (serverNonFull < minNonFullServers)
			return true;
		if (playerCount > 0 && maxPlayer/playerCount < newServerOnUserLoad)
			return false; // no demand because not enough players
		if (maxNonFullServers > 0 && serverNonFull <= maxNonFullServers)
			return false; // no demand because enough non full servers
		return true;
	}

	@Override
	public int getServerCount() {
		return servers.size();
	}
	
	@Override
	public void setTimeout(int timeout) {
		if (timeout < 0)
			throw new IllegalArgumentException("Timeout may not be lower than 0: " + timeout);
		this.timeout = timeout;
	}
	
	@Override
	public int getTimeout() {
		return timeout;
	}

	@Override
	public Server registerServer(UUID uuid, AtlasNode node) {
		if (uuid == null)
			throw new IllegalArgumentException("UUID can not be null!");
		if (node == null)
			throw new IllegalArgumentException("Node can not be null!");
		CoreServer server = new CoreServer(uuid, this, node);
		manager.addServer(server);
		return server;
	}

	@Override
	public synchronized void updatePlayerCount(Server server, int oldCount, int newCount) {
		playerCount += newCount-oldCount;
	}

	@Override
	public synchronized void updateMaxPlayerCount(Server server, int oldMax, int newMax) {
		maxPlayer += newMax-oldMax;
	}
	
	// config
	@Override
	public ServerConfig getServerConfig() {
		return config;
	}
	
	@Override
	public int getMaxNonFullServers() {
		return maxNonFullServers;
	}
	
	@Override
	public int getMinNonFullServers() {
		return minNonFullServers;
	}
	
	@Override
	public int getMaxServers() {
		return maxServers;
	}
	
	@Override
	public int getMinServers() {
		return minServers;
	}
	
	@Override
	public int getNewServerDelay() {
		return newServerDelay;
	}
	
	@Override
	public float getNewServerOnUserLoad() {
		return newServerOnUserLoad;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public boolean isMaintenance() {
		return maintenance;
	}

}
