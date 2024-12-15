package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.protocol.common.AbstractPacketPluginMessage;

@DefaultPacketID(packetID = PacketPlay.IN_PLUGIN_MESSAGE, definition = "plugin_message")
public class PacketInPluginMessage extends AbstractPacketPluginMessage implements PacketPlayIn {
	
	@Override
	public int getDefaultID() {
		return IN_PLUGIN_MESSAGE;
	}

}
