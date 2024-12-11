package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.protocol.common.AbstractPacketPluginMessage;

@DefaultPacketID(packetID = PacketPlay.OUT_PLUGIN_MESSAGE, definition = "custom_payload")
public class PacketOutPluginMessage extends AbstractPacketPluginMessage implements PacketPlayOut {
	
	@Override
	public int getDefaultID() {
		return OUT_PLUGIN_MESSAGE;
	}

}
