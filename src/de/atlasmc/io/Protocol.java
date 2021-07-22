package de.atlasmc.io;

public interface Protocol {
	
	public Packet createPacketIn(int id);
	public Packet createPacketOut(int id);
	public int getVersion();
	public PacketListener createDefaultPacketListener(Object o);
	public Packet createCopy(Packet packet);
	public <T extends Packet> T createPacket(Class<T> clazz);

}
