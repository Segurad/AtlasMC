package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;

@DefaultPacketID(PacketPlay.OUT_DISCONNECT)
public interface PacketOutDisconnect extends PacketPlay, PacketOutbound {
	
	public String getReason();
	
	@Override
	default int getDefaultID() {
		return OUT_DISCONNECT;
	}

}
