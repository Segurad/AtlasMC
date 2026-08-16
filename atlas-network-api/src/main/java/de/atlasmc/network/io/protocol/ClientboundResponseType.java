package de.atlasmc.network.io.protocol;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.network.NetworkType;

@DefaultPacketID(packetID = AtlasPacket.CLIENT_RESPONSE_TYPE, definition = "response_type")
public class ClientboundResponseType<T> extends AbstractPacket implements AtlasPacketClientbound {
	
	public NetworkType type;
	public int transactionID;
	public T data;
	
	@Override
	public int getDefaultID() {
		return CLIENT_RESPONSE_TYPE;
	}

}
