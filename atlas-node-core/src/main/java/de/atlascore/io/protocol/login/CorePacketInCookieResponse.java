package de.atlascore.io.protocol.login;

import de.atlascore.io.protocol.common.CoreAbstractPacketCookieResponse;
import de.atlasmc.io.Packet;
import de.atlasmc.io.protocol.login.PacketInCookieResponse;

public class CorePacketInCookieResponse extends CoreAbstractPacketCookieResponse<PacketInCookieResponse> {

	@Override
	public PacketInCookieResponse createPacketData() {
		return new PacketInCookieResponse();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketInCookieResponse.class);
	}

}
