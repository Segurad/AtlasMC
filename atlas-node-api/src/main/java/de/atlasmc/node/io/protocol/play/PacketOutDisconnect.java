package de.atlasmc.node.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.node.io.protocol.common.AbstractPacketDisconnect;

@DefaultPacketID(packetID = PacketPlay.OUT_DISCONNECT, definition = "disconnect")
public class PacketOutDisconnect extends AbstractPacketDisconnect implements PacketPlayOut {
	
	@Override
	public int getDefaultID() {
		return OUT_DISCONNECT;
	}

}
