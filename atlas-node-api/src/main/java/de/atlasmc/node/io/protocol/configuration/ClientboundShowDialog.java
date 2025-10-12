package de.atlasmc.node.io.protocol.configuration;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.node.io.protocol.common.AbstractPacketShowDialog;

@DefaultPacketID(packetID = PacketConfiguration.OUT_SHOW_DIALOG, definition = "show_dialog")
public class ClientboundShowDialog extends AbstractPacketShowDialog implements PacketConfigurationClientbound {

	@Override
	public int getDefaultID() {
		return PacketConfiguration.OUT_SHOW_DIALOG;
	}

}
