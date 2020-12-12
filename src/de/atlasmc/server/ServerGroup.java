package de.atlasmc.server;

import java.util.List;

public interface ServerGroup {
	
	public ServerConfig getServerConfig();
	public List<Server> getServers();
	public short getMaxServers();
	public short getMinServers();
	public GroupMode getMode();
	public int getPlayerCount();
	public short getPriority();
	public List<ServerPlayer> getPlayers();
	
	public enum GroupMode {
		DYNAMIC,
		STATIC;
	}

}
