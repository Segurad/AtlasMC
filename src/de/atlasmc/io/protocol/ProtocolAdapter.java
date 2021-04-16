package de.atlasmc.io.protocol;

import de.atlasmc.inventory.ItemStack;
import de.atlasmc.io.Packet;
import de.atlasmc.io.protocol.play.PacketOutSetSlot;

public interface ProtocolAdapter {

	public int getVersion();
	public String getVersionString();
	public Packet createPlayInPacket(int id);
	public Packet createPlayOutPacket(int id);
	public PacketOutSetSlot createPacketOutSetSlot(byte windowID, int slot, ItemStack item);
	
}
