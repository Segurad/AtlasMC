package de.atlasmc.node.io.protocol.play;

import de.atlasmc.NamespacedKey;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.IN_PROGRAM_JIGSAW_BLOCK, definition = "set_jigsaw_block")
public class PacketInProgramJigsawBlock extends AbstractPacket implements PacketPlayIn {
	
	public long position;
	public NamespacedKey name;
	public NamespacedKey target;
	public NamespacedKey pool;
	public String finalState;
	public String jointtype;
	public int selectionPriority;
	public int placementPriority;
	
	@Override
	public int getDefaultID() {
		return IN_PROGRAM_JIGSAW_BLOCK;
	}

}
