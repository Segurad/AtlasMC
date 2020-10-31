package de.atlasmc.io;

import java.net.Socket;

public interface ProtocolAdapter {

	public int getVersion();
	public String getVersionString();
	public ConnectionHandler createConnectionHandler(Socket socket);
	public Packet createPlayInPacket(int id);
	public Packet createPlayOutPacket(int id);
	
}
