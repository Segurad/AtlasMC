package de.atlasmc.core.node.io.protocol.configuration;

import de.atlasmc.core.node.io.protocol.common.CoreAbstractPacketStoreCookie;
import de.atlasmc.io.Packet;
import de.atlasmc.node.io.protocol.configuration.PacketOutStoreCookie;

public class CorePacketOutStoreCookie extends CoreAbstractPacketStoreCookie<PacketOutStoreCookie> {

	@Override
	public PacketOutStoreCookie createPacketData() {
		return new PacketOutStoreCookie();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutStoreCookie.class);
	}

}
