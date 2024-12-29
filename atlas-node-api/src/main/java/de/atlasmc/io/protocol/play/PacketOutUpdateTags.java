package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.protocol.common.AbstractPacketUpdateTags;

@DefaultPacketID(packetID = PacketPlay.OUT_UPDATE_TAGS, definition = "update_tags")
public class PacketOutUpdateTags extends AbstractPacketUpdateTags implements PacketPlayOut {

	@Override
	public int getDefaultID() {
		return OUT_UPDATE_TAGS;
	}

}
