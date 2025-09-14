package de.atlasmc.core.node.io.protocol.configuration;

import de.atlasmc.core.node.io.protocol.common.CoreAbstractPacketCookieRequest;
import de.atlasmc.io.Packet;
import de.atlasmc.node.io.protocol.configuration.PacketOutCookieRequest;

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
