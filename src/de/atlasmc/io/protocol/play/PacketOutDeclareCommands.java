package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;

@DefaultPacketID(PacketPlay.OUT_DECLARE_COMMANDS)
public interface PacketOutDeclareCommands extends PacketPlay, PacketOutbound {
	
	@Override
	default int getDefaultID() {
		return OUT_DECLARE_COMMANDS;
	}

}
