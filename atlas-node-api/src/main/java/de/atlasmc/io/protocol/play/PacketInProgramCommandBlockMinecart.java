package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.IN_PROGRAM_COMMAND_BLOCK_MINECART, definition = "set_command_minecart")
public class PacketInProgramCommandBlockMinecart extends AbstractPacket implements PacketPlayIn {
	
	public int entityID;
	public String command;
	public boolean trackOutput;
	
	@Override
	public int getDefaultID() {
		return IN_PROGRAM_COMMAND_BLOCK_MINECART;
	}

}
