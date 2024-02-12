package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.OUT_DELETE_MESSAGE)
public class PacketOutDeleteMessage extends AbstractPacket implements PacketPlayOut {

	public int messageID;
	public byte[] signature;
	
	@Override
	public int getDefaultID() {
		return OUT_DELETE_MESSAGE;
	}
	
}
