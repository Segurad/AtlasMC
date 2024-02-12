package de.atlasmc.io.protocol.configuration;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketConfiguration.IN_PONG)
public class PacketInPong extends AbstractPacket implements PacketConfigurationIn {

	public int pong;
	
	@Override
	public int getDefaultID() {
		return IN_PONG;
	}
	
}
