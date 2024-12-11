package de.atlascore.io.protocol.play;

import de.atlascore.io.protocol.common.CoreAbstractPacketAddResourcePack;
import de.atlasmc.io.Packet;
import de.atlasmc.io.protocol.play.PacketOutAddResourcePack;

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
