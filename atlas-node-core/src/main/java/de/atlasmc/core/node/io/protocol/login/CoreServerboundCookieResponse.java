package de.atlasmc.core.node.io.protocol.login;

import de.atlasmc.core.node.io.protocol.common.CoreAbstractPacketCookieResponse;
import de.atlasmc.io.Packet;
import de.atlasmc.node.io.protocol.login.ServerboundCookieResponse;

public class CoreServerboundCookieResponse extends CoreAbstractPacketCookieResponse<ServerboundCookieResponse> {

	@Override
	public ServerboundCookieResponse createPacketData() {
		return new ServerboundCookieResponse();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(ServerboundCookieResponse.class);
	}

}
