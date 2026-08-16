package de.atlasmc.core.master.server;

import java.util.Objects;
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
	private boolean maintenance;
	private GameState state;
	
	public CoreServer(UUID uuid, ServerGroup group, AtlasNode node) {
		this.uuid = Objects.requireNonNull(uuid);
		this.node = Objects.requireNonNull(node);
		this.group = group;
	}
	
	@Override
	public boolean isMaintenance() {
		return maintenance;
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
	public UUID getID() {
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
	public UUID getNodeID() {
		return node.getID();
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

	@Override
	public GameState getState() {
		return state;
	}
	
	@Override
	public void setState(GameState state) {
		this.state = state;
	}
	
	@Override
	public void setMaintenance(boolean value) {
		this.maintenance = value;
	}

	@Override
	public String getGroup() {
		return group == null ? null : group.getName();
	}

	@Override
	public ServerGroup getServerGroup() {
		return group;
	}

}
