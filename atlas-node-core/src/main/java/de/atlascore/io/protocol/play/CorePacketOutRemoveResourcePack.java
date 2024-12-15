package de.atlascore.io.protocol.play;

import de.atlascore.io.protocol.common.CoreAbstractPacketRemoveResourcePack;
import de.atlasmc.io.Packet;
import de.atlasmc.io.protocol.play.PacketOutRemoveResourcePack;

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
