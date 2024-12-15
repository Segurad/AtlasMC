package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.IN_JIGSAW_GENERATE, definition = "jigsaw_generate")
public class PacketInJigsawGenerate extends AbstractPacket implements PacketPlayIn {
	
	public long position;
	public int levels;
	public boolean keepJigsaws;
	
	@Override
	public int getDefaultID() {
		return IN_JIGSAW_GENERATE;
	}

}
