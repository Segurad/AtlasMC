package de.atlascore.io.protocol.configuration;

import de.atlascore.io.protocol.common.CoreAbstractPacketAddResourcePack;
import de.atlasmc.io.Packet;
import de.atlasmc.io.protocol.configuration.PacketOutAddResourcePack;

public class CorePacketOutAddResourcePack extends CoreAbstractPacketAddResourcePack<PacketOutAddResourcePack> {

	@Override
	public PacketOutAddResourcePack createPacketData() {
		return new PacketOutAddResourcePack();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutAddResourcePack.class);
	}

}
