package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketInbound;

@DefaultPacketID(PacketPlay.IN_SPECTATE)
public interface PacketInSpectate extends PacketPlay, PacketInbound {
	
	public String getUUID();
	
	@Override
	default int getDefaultID() {
		return IN_SPECTATE;
	}

}
