package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.OUT_SERVER_DATA)
public class PacketOutServerData extends AbstractPacket implements PacketPlayOut {

	public String motd;
	public byte[] icon;
	public boolean enforceSecureChat;
	
	@Override
	public int getDefaultID() {
		return OUT_SERVER_DATA;
	}
	
}
