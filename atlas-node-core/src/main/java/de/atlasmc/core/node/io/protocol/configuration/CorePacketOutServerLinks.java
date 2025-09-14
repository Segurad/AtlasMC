package de.atlasmc.core.node.io.protocol.configuration;

import de.atlasmc.core.node.io.protocol.common.CoreAbstractPacketServerLinks;
import de.atlasmc.io.Packet;
import de.atlasmc.node.io.protocol.configuration.PacketOutServerLinks;

public class CorePacketOutServerLinks extends CoreAbstractPacketServerLinks<PacketOutServerLinks> {

	@Override
	public PacketOutServerLinks createPacketData() {
		return new PacketOutServerLinks();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutServerLinks.class);
	}

}
