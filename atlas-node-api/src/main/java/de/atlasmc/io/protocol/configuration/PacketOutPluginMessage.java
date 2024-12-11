package de.atlasmc.io.protocol.configuration;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.protocol.common.AbstractPacketPluginMessage;

@DefaultPacketID(packetID = PacketConfiguration.OUT_PLUGIN_MESSAGE, definition = "custom_message")
public class PacketOutPluginMessage extends AbstractPacketPluginMessage implements PacketConfigurationOut {
	
	@Override
	public int getDefaultID() {
		return OUT_PLUGIN_MESSAGE;
	}
	
}
