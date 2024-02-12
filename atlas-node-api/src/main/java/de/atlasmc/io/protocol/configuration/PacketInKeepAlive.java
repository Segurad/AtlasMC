package de.atlasmc.io.protocol.configuration;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketConfiguration.IN_KEEP_ALIVE)
public class PacketInKeepAlive extends AbstractPacket implements PacketConfigurationIn {

	public long keepAliveID;
	
	@Override
	public int getDefaultID() {
		return IN_KEEP_ALIVE;
	}
	
}
