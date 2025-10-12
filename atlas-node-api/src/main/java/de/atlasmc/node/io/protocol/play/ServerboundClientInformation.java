package de.atlasmc.node.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.node.io.protocol.common.AbstractPacketClientInformation;

@DefaultPacketID(packetID = PacketPlay.IN_CLIENT_INFORMATION, definition = "client_information")
public class ServerboundClientInformation extends AbstractPacketClientInformation implements PacketPlayIn {
	
	public int getDefaultID() {
		return IN_CLIENT_INFORMATION;
	}

}
