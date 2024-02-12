package de.atlasmc.io.protocol.configuration;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketConfiguration.OUT_KEEP_ALIVE)
public class PacketOutKeepAlive extends AbstractPacket implements PacketConfigurationOut {

	public long keepAliveID;
	
	@Override
	public int getDefaultID() {
		return OUT_KEEP_ALIVE;
	}
	
}
