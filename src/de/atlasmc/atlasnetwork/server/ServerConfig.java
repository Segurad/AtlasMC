package de.atlasmc.atlasnetwork.server;

public class ServerConfig implements Cloneable {

	private int maxPlayers;
	private boolean stopWhenEmpty, safeChunks;
	private int stopTime;
	private String motd;
	
	public ServerConfig clone() {
		try {
			return (ServerConfig) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}

	public int getMaxPlayers() {
		return maxPlayers;
	}
}
