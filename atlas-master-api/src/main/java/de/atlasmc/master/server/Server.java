package de.atlasmc.master.server;

public interface Server extends de.atlasmc.network.server.Server {
	
	void setStatus(Status status);
	
	void setPlayerCount(int count);
	
	void setMaxPlayerCount(int count);

}
