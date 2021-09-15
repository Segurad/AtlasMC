package de.atlasmc.io;

public interface Protocol {
	
	public Packet createPacketIn(int id);
	public Packet createPacketOut(int id);
	public int getVersion();
	public PacketListener createDefaultPacketListener(Object o);
	public Packet createCopy(Packet packet);
	
	/**
	 * Creates a Packet of the Packet class.<br>
	 * Further requirements are defined by the implementation.<br>
	 * The default implementations are utilizing the {@link DefaultPacketID} annotation
	 * @param clazz
	 * @return
	 */
	public <T extends Packet> T createPacket(Class<T> clazz);

}
