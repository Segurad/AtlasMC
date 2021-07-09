package de.atlasmc.atlasnetwork.server;

import java.util.List;

import de.atlasmc.atlasnetwork.AtlasPlayer;

public interface ServerGroup {
	
	public ServerConfig getServerConfig();
	public List<Server> getServers();
	public short getMaxServers();
	public short getMinServers();
	public GroupMode getMode();
	public int getPlayerCount();
	public short getPriority();
	public List<AtlasPlayer> getPlayers();
	
	public enum GroupMode {
		DYNAMIC,
		STATIC;
	}

}
