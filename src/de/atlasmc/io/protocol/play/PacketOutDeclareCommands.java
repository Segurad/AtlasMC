package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.OUT_DECLARE_COMMANDS)
public class PacketOutDeclareCommands extends AbstractPacket implements PacketPlayOut {
	
	@Override
	public int getDefaultID() {
		return OUT_DECLARE_COMMANDS;
	}

}
