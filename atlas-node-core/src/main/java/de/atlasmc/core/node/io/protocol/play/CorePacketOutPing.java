package de.atlasmc.core.node.io.protocol.play;

import de.atlasmc.core.node.io.protocol.common.CoreAbstractPacketPing;
import de.atlasmc.io.Packet;
import de.atlasmc.node.io.protocol.play.PacketOutPing;

public class CorePacketOutPing extends CoreAbstractPacketPing<PacketOutPing>{

	@Override
	public PacketOutPing createPacketData() {
		return new PacketOutPing();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutPing.class);
	}

}
