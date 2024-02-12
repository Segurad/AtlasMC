package de.atlasmc.io.protocol.play;

import de.atlasmc.block.tile.CommandBlock.Mode;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.IN_PROGRAM_COMMAND_BLOCK)
public class PacketInProgramCommandBlock extends AbstractPacket implements PacketPlayIn {
	
	private long position;
	private String command;
	private Mode mode;
	private int flags;
	
	public long getPosition() {
		return position;
	}
	
	public void setPosition(long position) {
		this.position = position;
	}
	
	public String getCommand() {
		return command;
	}
	
	public void setCommand(String command) {
		this.command = command;
	}
	
	public Mode getMode() {
		return mode;
	}
	
	public void setMode(Mode mode) {
		this.mode = mode;
	}
	
	public int getFlags() {
		return flags;
	}
	
	public void setFlags(int flags) {
		this.flags = flags;
	}
	
	@Override
	public int getDefaultID() {
		return IN_PROGRAM_COMMAND_BLOCK;
	}

}
