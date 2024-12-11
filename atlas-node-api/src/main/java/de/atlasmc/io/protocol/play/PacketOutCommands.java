package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.OUT_COMMANDS, definition = "commands")
public class PacketOutCommands extends AbstractPacket implements PacketPlayOut {
	
	// TODO commands
	
	@Override
	public int getDefaultID() {
		return OUT_COMMANDS;
	}

}
