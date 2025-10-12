package de.atlasmc.node.io.protocol.configuration;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.node.io.protocol.common.AbstractPacketPluginMessage;

@DefaultPacketID(packetID = PacketConfiguration.IN_PLUGIN_MESSAGE, definition = "custom_payload")
public class ServerboundPluginMessage extends AbstractPacketPluginMessage implements PacketConfigurationServerbound {
	
	@Override
	public int getDefaultID() {
		return IN_PLUGIN_MESSAGE;
	}
	
}
