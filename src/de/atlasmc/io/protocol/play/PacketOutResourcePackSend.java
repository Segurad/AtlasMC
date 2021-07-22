package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;

@DefaultPacketID(PacketPlay.OUT_RESOURCE_PACK_SEND)
public interface PacketOutResourcePackSend extends PacketPlay, PacketOutbound {
	
	public String getURL();
	public String getHash();
	
	@Override
	default int getDefaultID() {
		return OUT_RESOURCE_PACK_SEND;
	}

}
