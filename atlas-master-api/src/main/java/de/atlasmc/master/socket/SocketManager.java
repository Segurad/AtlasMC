package de.atlasmc.master.socket;

import java.util.UUID;

import de.atlasmc.io.socket.SocketConfig;

public interface SocketManager {
	
	NodeSocket getSocket(UUID uuid);
	
	SocketConfig getSocketConfig(String name);
	
	boolean addSocketConfig(SocketConfig config);
	
	boolean removeSocketConfig(SocketConfig config);

}
