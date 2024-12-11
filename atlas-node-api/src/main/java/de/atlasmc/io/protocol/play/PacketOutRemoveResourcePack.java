package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.protocol.common.AbstractPacketRemoveResourcePack;

@DefaultPacketID(packetID = PacketPlay.OUT_REMOVE_RESOURCE_PACK, definition = "resource_pack_pop")
public class PacketOutRemoveResourcePack extends AbstractPacketRemoveResourcePack implements PacketPlayOut {

	@Override
	public int getDefaultID() {
		return OUT_REMOVE_RESOURCE_PACK;
	}

}
