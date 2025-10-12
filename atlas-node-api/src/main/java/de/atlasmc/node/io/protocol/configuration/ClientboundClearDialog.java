package de.atlasmc.node.io.protocol.configuration;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketConfiguration.OUT_CLEAR_DIALOG, definition = "clear_dialog")
public class ClientboundClearDialog extends AbstractPacket implements PacketConfigurationClientbound {

	@Override
	public int getDefaultID() {
		return PacketConfiguration.OUT_CLEAR_DIALOG;
	}

}
