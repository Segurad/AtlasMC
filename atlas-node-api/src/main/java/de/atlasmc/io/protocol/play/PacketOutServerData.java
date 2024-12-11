package de.atlasmc.io.protocol.play;

import de.atlasmc.chat.Chat;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.OUT_SERVER_DATA, definition = "server_data")
public class PacketOutServerData extends AbstractPacket implements PacketPlayOut {

	public Chat motd;
	public byte[] icon;
	
	@Override
	public int getDefaultID() {
		return OUT_SERVER_DATA;
	}
	
}
