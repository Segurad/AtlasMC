package de.atlasmc.core.node.io.protocol.play;

import de.atlasmc.core.node.io.protocol.common.CoreAbstractPacketText;
import de.atlasmc.io.Packet;
import de.atlasmc.node.io.protocol.play.PacketOutDisconnect;

public class CorePacketOutDisconnect extends CoreAbstractPacketText<PacketOutDisconnect> {

	@Override
	public PacketOutDisconnect createPacketData() {
		return new PacketOutDisconnect();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutDisconnect.class);
	}

}
