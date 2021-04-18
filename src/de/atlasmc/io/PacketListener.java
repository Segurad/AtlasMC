package de.atlasmc.io;

public interface PacketListener {
	
	public void handlePacket(Packet packet);
	public void handleUnregister();

}
