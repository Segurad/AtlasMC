package de.atlasmc.core.node.io.protocol.play;

import de.atlasmc.core.node.io.protocol.common.CoreAbstractPacketAddResourcePack;
import de.atlasmc.io.Packet;
import de.atlasmc.node.io.protocol.play.PacketOutAddResourcePack;

public class CorePacketOutAddRessourcePack extends CoreAbstractPacketAddResourcePack<PacketOutAddResourcePack> {

	@Override
	public PacketOutAddResourcePack createPacketData() {
		return new PacketOutAddResourcePack();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutAddResourcePack.class);
	}

}
