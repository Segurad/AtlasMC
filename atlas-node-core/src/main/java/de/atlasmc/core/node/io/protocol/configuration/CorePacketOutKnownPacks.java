package de.atlasmc.core.node.io.protocol.configuration;

import de.atlasmc.core.node.io.protocol.common.CoreAbstractPacketKnownPacks;
import de.atlasmc.io.Packet;
import de.atlasmc.node.io.protocol.configuration.PacketOutKnownPacks;

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
