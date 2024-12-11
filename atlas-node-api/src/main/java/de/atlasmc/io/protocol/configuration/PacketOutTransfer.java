package de.atlasmc.io.protocol.configuration;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.protocol.common.AbstractPacketTransfer;

@DefaultPacketID(packetID = PacketConfiguration.OUT_TRANSFER, definition = "transfer")
public class PacketOutTransfer extends AbstractPacketTransfer implements PacketConfigurationOut {
	
	@Override
	public int getDefaultID() {
		return PacketConfiguration.OUT_TRANSFER;
	}

}
