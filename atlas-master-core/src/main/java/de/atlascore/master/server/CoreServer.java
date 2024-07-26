package de.atlascore.master.server;

import java.util.UUID;

import de.atlasmc.master.node.AtlasNode;
import de.atlasmc.master.server.Server;
import de.atlasmc.master.server.ServerGroup;

public class CoreServer implements Server {

	private final UUID uuid;
	private final ServerGroup group;
	private final AtlasNode node;
	private String name;
	private String impl;
	private int playerCount;
	private int maxPlayer;
	private Status status;
	
	public CoreServer(UUID uuid, ServerGroup group, AtlasNode node) {
		this.uuid = uuid;
		this.group = group;
		this.node = node;
	}
	
	@Override
	public ServerGroup getGroup() {
		return group;
	}

	@Override
	public int getPlayerCount() {
		return playerCount;
	}

	@Override
	public int getMaxPlayers() {
		return maxPlayer;
	}

	@Override
	public UUID getServerID() {
		return uuid;
	}

	@Override
	public String getServerName() {
		return name;
	}

	@Override
	public AtlasNode getNode() {
		return node;
	}

	@Override
	public String getImplementationName() {
		return impl;
	}
	
	@Override
	public void setPlayerCount(int playerCount) {
		int currentCount = this.playerCount;
		if (currentCount == playerCount)
			return;
		group.updatePlayerCount(this, currentCount, playerCount);
		this.playerCount = playerCount;
	}
	
	@Override
	public void setMaxPlayerCount(int maxPlayer) {
		int currentMax = this.maxPlayer;
		if (currentMax == maxPlayer)
			return;
		group.updateMaxPlayerCount(this, currentMax, maxPlayer);
		this.maxPlayer = maxPlayer;
	}

	@Override
	public Status getStatus() {
		return status;
	}

	@Override
	public void setStatus(Status status) {
		this.status = status;
	}

}
