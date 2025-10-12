package de.atlasmc.core.node.io.protocol.configuration;

import de.atlasmc.core.node.io.protocol.common.CoreAbstractPacketCookieResponse;
import de.atlasmc.io.Packet;
import de.atlasmc.node.io.protocol.configuration.ServerboundCookieResponse;

public class CorePacketInCookieResponse extends CoreAbstractPacketCookieResponse<ServerboundCookieResponse> {

	@Override
	public ServerboundCookieResponse createPacketData() {
		return new ServerboundCookieResponse();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(ServerboundCookieResponse.class);
	}

}
