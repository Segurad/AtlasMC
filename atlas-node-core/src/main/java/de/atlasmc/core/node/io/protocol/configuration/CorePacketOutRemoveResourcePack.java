package de.atlasmc.core.node.io.protocol.configuration;

import de.atlasmc.core.node.io.protocol.common.CoreAbstractPacketRemoveResourcePack;
import de.atlasmc.io.Packet;
import de.atlasmc.node.io.protocol.configuration.PacketOutRemoveResourcePack;

public class CorePacketOutRemoveResourcePack extends CoreAbstractPacketRemoveResourcePack<PacketOutRemoveResourcePack> {

	@Override
	public PacketOutRemoveResourcePack createPacketData() {
		return new PacketOutRemoveResourcePack();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutRemoveResourcePack.class);
	}

}
