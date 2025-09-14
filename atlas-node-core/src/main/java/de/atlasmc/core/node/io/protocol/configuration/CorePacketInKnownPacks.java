package de.atlasmc.core.node.io.protocol.configuration;

import de.atlasmc.core.node.io.protocol.common.CoreAbstractPacketKnownPacks;
import de.atlasmc.io.Packet;
import de.atlasmc.node.io.protocol.configuration.PacketInKnownPacks;

public class CorePacketInKnownPacks extends CoreAbstractPacketKnownPacks<PacketInKnownPacks> {

	@Override
	public PacketInKnownPacks createPacketData() {
		return new PacketInKnownPacks();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketInKnownPacks.class);
	}

}
