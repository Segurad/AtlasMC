package de.atlascore.server;

import java.io.File;
import java.util.UUID;

import de.atlasmc.LocalAtlasNode;
import de.atlasmc.AtlasNode;
import de.atlasmc.atlasnetwork.server.ServerConfig;
import de.atlasmc.atlasnetwork.server.ServerGroup;
import de.atlasmc.server.NodeServer;

public abstract class CoreAbstractNodeServer implements NodeServer {
	
	protected final ServerGroup group;
	protected final ServerConfig config;
	protected final UUID serverID;
	protected final String name;
	protected final File workdir;
	protected final File worldDir;
	protected volatile Status status;
	
	public CoreAbstractNodeServer(UUID serverID, File workdir, File worldDir, ServerGroup group) {
		this(serverID, workdir, worldDir, group, group.getServerConfig().clone());
	}
	
	public CoreAbstractNodeServer(UUID serverID, File workdir, File worldDir, ServerConfig config) {
		this(serverID, workdir, worldDir, null, config);
	}
	
	protected CoreAbstractNodeServer(UUID serverID, File workdir, File worldDir, ServerGroup group, ServerConfig config) {
		if (serverID == null)
			throw new IllegalArgumentException("Server id can not be null!");
		if (config == null)
			throw new IllegalArgumentException("Config can not be null!");
		if (workdir == null)
			throw new IllegalArgumentException("Workdir can not be null!");
		if (worldDir == null)
			throw new IllegalArgumentException("Worlddir can not be null!");
		this.workdir = workdir;
		this.worldDir = worldDir;
		this.config = config;
		this.group = group;
		this.name = group.getName() + "-" + serverID.toString();
		this.serverID = serverID;
	}

	@Override
	public ServerGroup getGroup() {
		return group;
	}
	
	@Override
	public File getWorlddir() {
		return worldDir;
	}

	@Override
	public UUID getServerID() {
		return serverID;
	}

	@Override
	public String getServerName() {
		return name;
	}

	@Override
	public LocalAtlasNode getNode() {
		return AtlasNode.getAtlas();
	}

	@Override
	public Status getStatus() {
		return status;
	}

	@Override
	public File getWorkdir() {
		return workdir;
	}

	@Override
	public boolean isRunning() {
		return status == Status.ONLINE;
	}

}
