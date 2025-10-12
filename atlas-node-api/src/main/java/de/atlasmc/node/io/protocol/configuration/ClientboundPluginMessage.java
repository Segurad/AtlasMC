package de.atlasmc.node.io.protocol.configuration;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.node.io.protocol.common.AbstractPacketPluginMessage;

@DefaultPacketID(packetID = PacketConfiguration.OUT_PLUGIN_MESSAGE, definition = "custom_payload")
public class ClientboundPluginMessage extends AbstractPacketPluginMessage implements PacketConfigurationClientbound {
	
	@Override
	public int getDefaultID() {
		return OUT_PLUGIN_MESSAGE;
	}
	
}
