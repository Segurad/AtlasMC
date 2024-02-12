package de.atlascore.atlasnetwork.master.server;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import de.atlascore.atlasnetwork.CoreAbstractServerGroup;
import de.atlascore.atlasnetwork.CoreAtlasNode;
import de.atlasmc.NamespacedKey;
import de.atlasmc.atlasnetwork.server.Server;
import de.atlasmc.tick.Tickable;
import de.atlasmc.util.concurrent.future.CompleteFuture;
import de.atlasmc.util.concurrent.future.Future;
import de.atlasmc.util.configuration.ConfigurationSection;

public class CoreServerGroup extends CoreAbstractServerGroup implements Tickable {
	
	// node selector
	private final float memoryUtilisation;
	private final int memoryThreshold;
	private final NamespacedKey deploymentMethod;
	private final boolean internal;
	
	private int maxPlayer;
	private int playerCount;
	private int serverFull;
	
	private int ticksUnsatisfied;
	private int timeout;
	
	private final Map<UUID, CoreServer> servers;
	
	public CoreServerGroup(String name, ConfigurationSection config) {
		super(name, config);
		ConfigurationSection selectorCfg = config.getConfigurationSection("node-selector");
		this.deploymentMethod = NamespacedKey.of(selectorCfg.getString("method", "atlas-master:server/default_deployment"));
		this.internal = selectorCfg.getBoolean("internal", true);
		this.memoryThreshold = selectorCfg.getInt("memory-threshold");
		this.memoryUtilisation = selectorCfg.getFloat("memory-utilisation");
		this.servers = new ConcurrentHashMap<>();
	}
	
	// runtime
	
	@Override
	public int getPlayerCount() {
		return playerCount;
	}

	@Override
	public Future<Server> getServer(UUID uuid) {
		if (uuid == null)
			throw new IllegalArgumentException("UUID can not be null!");
		Server server = servers.get(uuid);
		return server != null ? new CompleteFuture<>(server) : CompleteFuture.getNullFuture();
	}
	
	@Override
	public int getCurrentPlayerCapacity() {
		return maxPlayer;
	}
	
	public NamespacedKey getDeploymentMethod() {
		return deploymentMethod;
	}
	
	public int getMemoryThreshold() {
		return memoryThreshold;
	}
	
	public float getMemoryUtilisation() {
		return memoryUtilisation;
	}
	
	public boolean isInternal() {
		return internal;
	}
	
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
	
	void updatePlayerCount(int oldCount, int newCount) {
		playerCount += newCount-oldCount;
	}
	
	void updateMaxPlayerCount(int oldCount, int newCount) {
		maxPlayer += newCount-oldCount;
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
	
	public void setTimeout(int timeout) {
		if (timeout < 0)
			throw new IllegalArgumentException("Timeout may not be lower than 0: " + timeout);
		this.timeout = timeout;
	}
	
	public int getTimeout() {
		return timeout;
	}

	public CoreServer registerServer(UUID uuid, CoreAtlasNode node) {
		if (uuid == null)
			throw new IllegalArgumentException("UUID can not be null!");
		if (node == null)
			throw new IllegalArgumentException("Node can not be null!");
		CoreServer server = new CoreServer(uuid, this, node);
		return server;
	}

}
