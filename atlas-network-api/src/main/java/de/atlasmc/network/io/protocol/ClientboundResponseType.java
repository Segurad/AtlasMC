package de.atlasmc.network.io.protocol;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.network.NetworkType;

@DefaultPacketID(packetID = AtlasPacket.CLIENT_RESPONSE_TYPE, definition = "response_type")
public class ClientboundResponseType extends AbstractPacket implements AtlasPacketClientbound {
	
	public NetworkType type;
	public int transactionID;
	public Object data;
	
	@Override
	public int getDefaultID() {
		return CLIENT_RESPONSE_TYPE;
	}

}
