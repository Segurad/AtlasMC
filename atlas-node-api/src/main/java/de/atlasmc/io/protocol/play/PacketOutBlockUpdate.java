package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.OUT_BLOCK_UPDATE, definition = "block_update")
public class PacketOutBlockUpdate extends AbstractPacket implements PacketPlayOut {
	
	public long position;
	public int blockStateID;
	
	@Override
	public int getDefaultID() {
		return OUT_BLOCK_UPDATE;
	}

}
