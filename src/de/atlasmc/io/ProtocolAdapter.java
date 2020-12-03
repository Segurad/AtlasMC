package de.atlasmc.io;

import java.net.Socket;

import de.atlasmc.inventory.ItemStack;
import de.atlasmc.io.protocol.play.PacketOutSetSlot;

public interface ProtocolAdapter {

	public int getVersion();
	public int getConnectionCount();
	public void setConnectionCount(int count);
	public String getVersionString();
	public ConnectionHandler createConnectionHandler(Socket socket);
	public Packet createPlayInPacket(int id);
	public Packet createPlayOutPacket(int id);
	public PacketOutSetSlot createPacketOutSetSlot(byte windowID, int slot, ItemStack item);
	
}
