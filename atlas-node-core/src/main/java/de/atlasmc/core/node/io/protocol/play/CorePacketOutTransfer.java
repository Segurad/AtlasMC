package de.atlasmc.core.node.io.protocol.play;

import de.atlasmc.core.node.io.protocol.common.CoreAbstractPacketTransfer;
import de.atlasmc.io.Packet;
import de.atlasmc.node.io.protocol.play.PacketOutTransfer;

public class CorePacketOutTransfer extends CoreAbstractPacketTransfer<PacketOutTransfer> {

	@Override
	public PacketOutTransfer createPacketData() {
		return new PacketOutTransfer();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutTransfer.class);
	}

}
