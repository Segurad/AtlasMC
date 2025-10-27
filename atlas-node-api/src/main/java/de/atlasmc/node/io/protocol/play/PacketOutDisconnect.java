package de.atlasmc.node.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.node.io.protocol.common.AbstractPacketText;

@DefaultPacketID(packetID = PacketPlay.OUT_DISCONNECT, definition = "disconnect")
public class PacketOutDisconnect extends AbstractPacketText implements PacketPlayOut {
	
	@Override
	public int getDefaultID() {
		return OUT_DISCONNECT;
	}

}
