package de.atlasmc.io.protocol.configuration;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketConfiguration.OUT_DISCONNECT)
public class PacketOutDisconnect extends AbstractPacket implements PacketConfigurationOut {

	public String reason;
	
	@Override
	public int getDefaultID() {
		return OUT_DISCONNECT;
	}
	
}
