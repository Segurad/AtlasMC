package de.atlasmc.io.protocol.configuration;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketConfiguration.OUT_RESOURCE_PACK)
public class PacketOutResourcePack extends AbstractPacket implements PacketConfigurationOut {

	public String url;
	public String hash;
	public boolean force;
	public String message;
	
	@Override
	public int getDefaultID() {
		return OUT_RESOURCE_PACK;
	}
	
}
