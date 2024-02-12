package de.atlasmc.io.protocol.configuration;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketConfiguration.OUT_PING)
public class PacketOutPing extends AbstractPacket implements PacketConfigurationOut {

	public int ping;
	
	@Override
	public int getDefaultID() {
		return OUT_PING;
	}
	
}
