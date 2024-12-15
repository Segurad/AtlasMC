package de.atlascore.io.protocol.configuration;

import de.atlascore.io.protocol.common.CoreAbstractPacketKnownPacks;
import de.atlasmc.io.Packet;
import de.atlasmc.io.protocol.configuration.PacketInKnownPacks;

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
