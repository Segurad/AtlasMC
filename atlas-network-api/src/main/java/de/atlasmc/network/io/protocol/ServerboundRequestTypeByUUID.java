package de.atlasmc.network.io.protocol;

import java.util.UUID;

import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = AtlasPacket.SERVER_REQUEST_TYPE_BY_UUID, definition = "request_by_uuid")
public class ServerboundRequestTypeByUUID extends AbstractPacketRequestType implements AtlasPacketServerbound {

	public UUID uuid;
	
	@Override
	public int getDefaultID() {
		return SERVER_REQUEST_TYPE_BY_UUID;
	}

}
