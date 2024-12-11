package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.OUT_BUNDLE_DELIMITER, definition = "bundle_delimiter")
public class PacketOutBundleDelimiter extends AbstractPacket implements PacketPlayOut {

	@Override
	public int getDefaultID() {
		return OUT_BUNDLE_DELIMITER;
	}
	
}
