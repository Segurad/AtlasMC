package de.atlasmc.network.io.protocol;

import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = AtlasPacket.SERVER_REQUEST_TYPE_BY_NAME, definition = "request_by_name")
public class ServerboundRequestTypeByName extends AbstractPacketRequestType implements AtlasPacketServerbound {

	public String name;
	
	@Override
	public int getDefaultID() {
		return SERVER_REQUEST_TYPE_BY_NAME;
	}

}
