package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.OUT_SET_BLOCK_DESTROY_STAGE, definition = "block_destruction")
public class PacketOutSetBlockDestroyStage extends AbstractPacket implements PacketPlayOut {
	
	public int entityID;
	public long position;
	public int stage;
	
	@Override
	public int getDefaultID() {
		return OUT_SET_BLOCK_DESTROY_STAGE;
	}

}
