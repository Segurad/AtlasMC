package de.atlasmc.core.node.server;

import java.io.File;
import java.util.Objects;
import java.util.UUID;

import de.atlasmc.chat.Chat;
import de.atlasmc.network.server.ServerConfig;
import de.atlasmc.network.server.ServerGroup;
import de.atlasmc.node.AtlasNode;
import de.atlasmc.node.server.NodeServer;

public abstract class CoreAbstractNodeServer implements NodeServer {
	
	protected final ServerGroup group;
	protected final ServerConfig config;
	protected final UUID serverID;
	protected final String name;
	protected final File workdir;
	protected final File worldDir;
	protected volatile Status status;
	protected volatile GameState state;
	protected volatile Chat motd;
	
	public CoreAbstractNodeServer(UUID serverID, File workdir, File worldDir, ServerGroup group) {
		this(serverID, workdir, worldDir, group, group.getServerConfig().clone());
	}
	
	public CoreAbstractNodeServer(UUID serverID, File workdir, File worldDir, ServerConfig config) {
		this(serverID, workdir, worldDir, null, config);
	}
	
	protected CoreAbstractNodeServer(UUID serverID, File workdir, File worldDir, ServerGroup group, ServerConfig config) {
		this.serverID = Objects.requireNonNull(serverID, "serverID");
		this.workdir = Objects.requireNonNull(workdir, "workdir");
		this.worldDir = Objects.requireNonNull(worldDir, "worldDir");
		this.config = Objects.requireNonNull(config, "config");
		this.group = group;
		this.name = (group != null ? group.getName() : "Server") + "-" + serverID.toString();
	}
	
	@Override
	public boolean isMaintenance() {
		return config.isMaintenance();
	}
	
	@Override
	public ServerGroup getServerGroup() {
		return group;
	}
	
	@Override
	public String getGroup() {
		return group != null ? group.getName() : null;
	}
	
	@Override
	public File getWorlddir() {
		return worldDir;
	}

	@Override
	public UUID getID() {
		return serverID;
	}

	@Override
	public String getServerName() {
		return name;
	}

	@Override
	public UUID getNodeID() {
		return AtlasNode.getAtlas().getID();
	}

	@Override
	public Status getStatus() {
		return status;
	}
	
	@Override
	public GameState getState() {
		return state;
	}

	@Override
	public File getWorkdir() {
		return workdir;
	}

}
