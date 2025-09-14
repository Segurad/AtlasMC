package de.atlasmc.node.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.node.io.protocol.common.AbstractPacketTransfer;

@DefaultPacketID(packetID = PacketPlay.OUT_TRANSFER, definition = "transfer")
public class PacketOutTransfer extends AbstractPacketTransfer implements PacketPlayOut {

	@Override
	public int getDefaultID() {
		return OUT_TRANSFER;
	}
	
}
