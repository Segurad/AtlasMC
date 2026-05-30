package de.atlasmc.node.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.node.block.tile.CommandBlock.Mode;

@DefaultPacketID(packetID = PacketPlay.IN_PROGRAM_COMMAND_BLOCK, definition = "set_command_block")
public class PacketInProgramCommandBlock extends AbstractPacket implements PacketPlayIn {
	
	public static final int
	FLAG_TRACK_OUTPUT = 0x01,
	FLAG_IS_CONDITIONAL = 0x02,
	FLAG_AUTOMATIC = 0x04;
	
	public long position;
	public String command;
	public Mode mode;
	public int flags;
	
	@Override
	public int getDefaultID() {
		return IN_PROGRAM_COMMAND_BLOCK;
	}

}
