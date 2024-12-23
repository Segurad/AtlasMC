package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.protocol.common.AbstractPacketAddResourcePack;

@DefaultPacketID(packetID = PacketPlay.OUT_ADD_RESOURCE_PACK, definition = "resource_pack_push")
public class PacketOutAddResourcePack extends AbstractPacketAddResourcePack implements PacketPlayOut {

	@Override
	public int getDefaultID() {
		return OUT_ADD_RESOURCE_PACK;
	}

}
