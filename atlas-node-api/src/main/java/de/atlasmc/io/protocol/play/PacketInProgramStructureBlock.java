package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.IN_PROGRAM_STRUCTURE_BLOCK, definition = "set_structure_block")
public class PacketInProgramStructureBlock extends AbstractPacket implements PacketPlayIn {
	
	public long position;
	public int action;
	public int mode;
	public String name;
	public int offsetX;
	public int offsetY;
	public int offsetZ;
	public int sizeX;
	public int sizeY;
	public int sizeZ;
	public int mirror;
	public int rotation;
	public String metadata;
	public float integrity;
	public long seed;
	public int flags;

	@Override
	public int getDefaultID() {
		return IN_PROGRAM_STRUCTURE_BLOCK;
	}
	
}
