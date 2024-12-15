package de.atlascore.io.protocol.configuration;

import de.atlascore.io.protocol.common.CoreAbstractPacketKnownPacks;
import de.atlasmc.io.Packet;
import de.atlasmc.io.protocol.configuration.PacketOutKnownPacks;

public class CorePacketOutKnownPacks extends CoreAbstractPacketKnownPacks<PacketOutKnownPacks> {

	@Override
	public PacketOutKnownPacks createPacketData() {
		return new PacketOutKnownPacks();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutKnownPacks.class);
	}

}
