package de.atlascore.atlasnetwork.master.server;

import java.util.UUID;

import de.atlascore.atlasnetwork.CoreAtlasNode;
import de.atlasmc.atlasnetwork.server.Server;
import de.atlasmc.atlasnetwork.server.ServerGroup;

public class CoreServer implements Server {

	private final UUID uuid;
	private final CoreServerGroup group;
	private final CoreAtlasNode node;
	private String name;
	private String impl;
	private int playerCount;
	private int maxPlayer;
	
	public CoreServer(UUID uuid, CoreServerGroup group, CoreAtlasNode node) {
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
	public CoreAtlasNode getNode() {
		return node;
	}

	@Override
	public String getImplementationName() {
		return impl;
	}
	
	public void updatePlayerCount(int playerCount) {
		group.updatePlayerCount(this.playerCount, playerCount);
		this.playerCount = playerCount;
	}
	
	public void updateMaxPlayerCount(int maxPlayer) {
		group.updateMaxPlayerCount(this.maxPlayer, maxPlayer);
		this.maxPlayer = maxPlayer;
	}

	@Override
	public Status getStatus() {
		// TODO Auto-generated method stub
		return null;
	}

}
