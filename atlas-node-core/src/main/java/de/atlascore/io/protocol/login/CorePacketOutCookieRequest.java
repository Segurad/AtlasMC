package de.atlascore.io.protocol.login;

import de.atlascore.io.protocol.common.CoreAbstractPacketCookieRequest;
import de.atlasmc.io.Packet;
import de.atlasmc.io.protocol.login.PacketOutCookieRequest;

public class CorePacketOutCookieRequest extends CoreAbstractPacketCookieRequest<PacketOutCookieRequest> {

	@Override
	public PacketOutCookieRequest createPacketData() {
		return new PacketOutCookieRequest();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutCookieRequest.class);
	}

}
