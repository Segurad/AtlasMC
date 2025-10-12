package de.atlasmc.node.io.protocol.configuration;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketConfiguration.OUT_RESET_CHAT, definition = "reset_chat")
public class ClientboundResetChat extends AbstractPacket implements PacketConfigurationClientbound {
	
	@Override
	public int getDefaultID() {
		return PacketConfiguration.OUT_RESET_CHAT;
	}

}
