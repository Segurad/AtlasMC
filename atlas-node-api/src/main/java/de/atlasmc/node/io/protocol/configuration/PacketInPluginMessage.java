package de.atlasmc.node.io.protocol.configuration;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.node.io.protocol.common.AbstractPacketPluginMessage;

@DefaultPacketID(packetID = PacketConfiguration.IN_PLUGIN_MESSAGE, definition = "custom_payload")
public class PacketInPluginMessage extends AbstractPacketPluginMessage implements PacketConfigurationIn {
	
	@Override
	public int getDefaultID() {
		return IN_PLUGIN_MESSAGE;
	}
	
}
