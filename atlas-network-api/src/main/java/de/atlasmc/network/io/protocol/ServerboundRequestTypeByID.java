package de.atlasmc.network.io.protocol;

import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = AtlasPacket.SERVER_REQUEST_TYPE_BY_UUID, definition = "request_by_id")
public class ServerboundRequestTypeByID extends AbstractPacketRequestType implements AtlasPacketServerbound {
	
	public int id;
	
	public ServerboundRequestTypeByID() {
		this(0);
	}
	
	public ServerboundRequestTypeByID(int id) {
		this.id = id;
	}
	
	@Override
	public int getDefaultID() {
		return SERVER_REQUEST_TYPE_BY_UUID;
	}

}
