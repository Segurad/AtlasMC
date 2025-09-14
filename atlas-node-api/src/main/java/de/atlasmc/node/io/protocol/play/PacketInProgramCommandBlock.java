package de.atlasmc.node.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.node.block.tile.CommandBlock.Mode;

@DefaultPacketID(packetID = PacketPlay.IN_PROGRAM_COMMAND_BLOCK, definition = "set_command_block")
public class PacketInProgramCommandBlock extends AbstractPacket implements PacketPlayIn {
	
	public long position;
	public String command;
	public Mode mode;
	/**
	 * <ul>
	 * <li>0x01 = track output</li>
	 * <li>0x02 = is conditional</li>
	 * <li>0x04 = automatic</li>
	 * </ul>
	 */
	public int flags;
	
	@Override
	public int getDefaultID() {
		return IN_PROGRAM_COMMAND_BLOCK;
	}

}
