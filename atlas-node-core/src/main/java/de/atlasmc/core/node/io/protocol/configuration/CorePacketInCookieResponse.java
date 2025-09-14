package de.atlasmc.core.node.io.protocol.configuration;

import de.atlasmc.core.node.io.protocol.common.CoreAbstractPacketCookieResponse;
import de.atlasmc.io.Packet;
import de.atlasmc.node.io.protocol.configuration.PacketInCookieResponse;

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
