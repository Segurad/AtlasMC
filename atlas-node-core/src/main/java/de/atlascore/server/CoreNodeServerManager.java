package de.atlascore.server;

import java.io.File;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import de.atlasmc.Atlas;
import de.atlasmc.atlasnetwork.server.ServerConfig;
import de.atlasmc.atlasnetwork.server.ServerGroup;
import de.atlasmc.registry.Registries;
import de.atlasmc.registry.Registry;
import de.atlasmc.server.NodeServer;
import de.atlasmc.server.NodeServerManager;
import de.atlasmc.server.ServerDeploymentException;
import de.atlasmc.server.ServerFactory;
import de.atlasmc.util.FileUtils;
import de.atlasmc.util.map.CopyOnWriteArrayListMultimap;
import de.atlasmc.util.map.Multimap;

public class CoreNodeServerManager implements NodeServerManager {
	
	private final Map<UUID, NodeServer> servers;
	private final Multimap<ServerGroup, NodeServer> serverByGroup;
	private final Map<String, ServerGroup> groups;
	private final File tmpServerPath;
	private final File staticServerPath;
	
	public CoreNodeServerManager(File workDir) {
		this.servers = new ConcurrentHashMap<>();
		this.groups = new ConcurrentHashMap<>();
		this.serverByGroup = new CopyOnWriteArrayListMultimap<>();
		tmpServerPath = new File(workDir, "tmp/servers/");
		FileUtils.ensureDir(tmpServerPath);
		staticServerPath = new File(workDir, "servers/");
		FileUtils.ensureDir(staticServerPath);
	}
	
	@Override
	public Collection<NodeServer> getServers() {
		return servers.values();
	}

	@Override
	public NodeServer getServer(UUID uuid) {
		return servers.get(uuid);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends NodeServer> T getServer(UUID uuid, Class<T> clazz) {
		NodeServer server = servers.get(uuid);
		if (clazz.isInstance(server))
			return (T) server;
		return null;
	}

	@Override
	public Collection<NodeServer> getServers(ServerGroup group) {
		return serverByGroup.get(group);
	}

	@Override
	public NodeServer deployServer(ServerGroup group) {
		Atlas.getLogger().debug("Deploing servergroup: ", group.getName());
		ServerConfig config = group.getServerConfig();
		Registry<ServerFactory> registry = Registries.getInstanceRegistry(ServerFactory.class);
		ServerFactory factory = registry.get(config.getFactory());
		if (factory == null)
			throw new ServerDeploymentException("ServerFactory not found: " + config.getFactory());
		UUID uuid = UUID.randomUUID();
		File workDir = null;
		if (config.isStatic()) {
			workDir = new File(staticServerPath, group.getName() + "-" + uuid.toString());
		} else {
			workDir = new File(tmpServerPath, group.getName() + "-" + uuid.toString());
		}
		FileUtils.ensureDir(workDir);
		NodeServer server = factory.createServer(uuid, workDir, group);
		server.prepare().setListener((future) -> {
			if (future.getNow()) {
				servers.put(uuid, server);
				serverByGroup.put(group, server);
				Atlas.getLogger().info("Starting server {}-{}", group.getName(), uuid);
				server.start();
			}
		});
		return server;
	}

	@Override
	public boolean registerServerGroup(ServerGroup group) {
		if (group == null)
			throw new IllegalArgumentException("Group can not be null!");
		return groups.put(group.getName(), group) != group;
	}

	@Override
	public boolean unregisterServerGroup(ServerGroup group) {
		if (group == null)
			throw new IllegalArgumentException("Group can not be null!");
		return groups.remove(group.getName(), group);
	}

	@Override
	public Collection<ServerGroup> getServerGroups() {
		return groups.values();
	}

	@Override
	public ServerGroup getServerGroup(String name) {
		if (name == null)
			throw new IllegalArgumentException("Name can not be null!");
		return groups.get(name);
	}

}
