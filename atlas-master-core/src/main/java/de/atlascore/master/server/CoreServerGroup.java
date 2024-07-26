package de.atlascore.master.server;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import de.atlasmc.NamespacedKey;
import de.atlasmc.atlasnetwork.server.ServerConfig;
import de.atlasmc.io.IOReadable;
import de.atlasmc.io.IOWriteable;
import de.atlasmc.master.node.AtlasNode;
import de.atlasmc.master.server.Server;
import de.atlasmc.master.server.ServerGroup;
import de.atlasmc.tick.Tickable;
import de.atlasmc.util.configuration.ConfigurationSection;
import de.atlasmc.util.configuration.ConfigurationSerializeable;
import io.netty.buffer.ByteBuf;

public class CoreServerGroup implements Tickable, ServerGroup, ConfigurationSerializeable, IOReadable, IOWriteable {
	
	protected String name;
	protected ServerConfig config;
	protected int maxServers;
	protected int maxNonFullServers;
	protected int minServers;
	protected int minNonFullServers;
	protected int newServerDelay;
	protected float newServerOnUserLoad;
	protected volatile boolean maintenance;
	
	// node selector
	private float memoryUtilisation;
	private int memoryThreshold;
	private NamespacedKey deploymentMethod;
	private boolean internal;
	
	private int maxPlayer;
	private int playerCount;
	private int serverFull;
	
	private int ticksUnsatisfied;
	private int timeout;
	
	private final Map<UUID, CoreServer> servers;
	
	public CoreServerGroup(ConfigurationSection config) {
		if (config == null)
			throw new IllegalArgumentException("Config can not be null!");
		this.name = config.getString("name");
		minServers = config.getInt("min-server");
		maxServers = config.getInt("max-server");
		newServerDelay = config.getInt("new-server-delay");
		newServerOnUserLoad = config.getFloat("new-server-on-user-load");
		maxNonFullServers = config.getInt("max-non-full-server");
		minNonFullServers = config.getInt("min-non-full-server");
		maintenance = config.getBoolean("maintenance");
		ConfigurationSection serverCfg = config.getConfigurationSection("server-config");
		this.config = new ServerConfig(serverCfg);
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
	
	/**
	 * Returns whether or not this group is unsatisfied.
	 * @return true if unsatisfied
	 */
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
	
	/**
	 * Sets a timeout in ticks until this group becomes active again
	 * If timeouted this group will not return true on {@link #isUnsatisfied()}
	 * @param timeout
	 */
	public void setTimeout(int timeout) {
		if (timeout < 0)
			throw new IllegalArgumentException("Timeout may not be lower than 0: " + timeout);
		this.timeout = timeout;
	}
	
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

	@Override
	public void write(ByteBuf buf) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void read(ByteBuf buf) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <T extends ConfigurationSection> T toConfiguration(T configuration) {
		// TODO Auto-generated method stub
		return null;
	}

}
