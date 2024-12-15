package de.atlascore.io.protocol.configuration;

import de.atlascore.io.protocol.common.CoreAbstractPacketStoreCookie;
import de.atlasmc.io.Packet;
import de.atlasmc.io.protocol.configuration.PacketOutStoreCookie;

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
