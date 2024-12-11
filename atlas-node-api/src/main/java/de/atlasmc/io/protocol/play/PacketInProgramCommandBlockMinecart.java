package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.IN_PROGRAM_COMMAND_BLOCK_MINECART)
public class PacketInProgramCommandBlockMinecart extends AbstractPacket implements PacketPlayIn {
	
	private int entityID;
	private String command;
	private boolean trackOutput;
	
	public int getEntityID() {
		return entityID;
	}
	
	public void setEntityID(int entityID) {
		this.entityID = entityID;
	}
	
	public String getCommand() {
		return command;
	}
	
	public void setCommand(String command) {
		this.command = command;
	}
	
	public boolean getTrackOutput() {
		return trackOutput;
	}
	
	public void setTrackOutput(boolean trackOutput) {
		this.trackOutput = trackOutput;
	}
	
	@Override
	public int getDefaultID() {
		return IN_PROGRAM_COMMAND_BLOCK_MINECART;
	}

}
