package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.IN_MESSAGE_ACKNOWLEDGEMENT)
public class PacketInMessageAcknowledgment extends AbstractPacket implements PacketPlayIn {

	public int messageID;
	
	@Override
	public int getDefaultID() {
		return PacketPlay.IN_MESSAGE_ACKNOWLEDGEMENT;
	}

}
