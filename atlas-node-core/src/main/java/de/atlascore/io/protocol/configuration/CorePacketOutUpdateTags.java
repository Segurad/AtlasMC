package de.atlascore.io.protocol.configuration;

import de.atlascore.io.protocol.common.CoreAbstractPacketUpdateTags;
import de.atlasmc.io.Packet;
import de.atlasmc.io.protocol.configuration.PacketOutUpdateTags;

public class CorePacketOutUpdateTags extends CoreAbstractPacketUpdateTags<PacketOutUpdateTags> {

	@Override
	public PacketOutUpdateTags createPacketData() {
		return new PacketOutUpdateTags();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutUpdateTags.class);
	}

}
