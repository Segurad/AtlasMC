package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.IN_UPDATE_SIGN, definition = "update_sign")
public class PacketInUpdateSign extends AbstractPacket implements PacketPlayIn {
	
	public long position;
	public boolean isFront;
	public String line1;
	public String line2;
	public String line3;
	public String line4;
	
	@Override
	public int getDefaultID() {
		return IN_UPDATE_SIGN;
	}

}
