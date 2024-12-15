package de.atlascore.io.protocol.play;

import de.atlascore.io.protocol.common.CoreAbstractPacketServerLinks;
import de.atlasmc.io.Packet;
import de.atlasmc.io.protocol.play.PacketOutServerLinks;

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
