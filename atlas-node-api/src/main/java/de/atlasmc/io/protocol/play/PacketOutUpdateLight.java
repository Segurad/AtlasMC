package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.protocol.common.AbstractPacketChunkLight;

@DefaultPacketID(packetID = PacketPlay.OUT_UPDATE_LIGHT, definition = "light_update")
public class PacketOutUpdateLight extends AbstractPacketChunkLight implements PacketPlayOut {

	@Override
	public int getDefaultID() {
		return OUT_UPDATE_LIGHT;
	}

}
