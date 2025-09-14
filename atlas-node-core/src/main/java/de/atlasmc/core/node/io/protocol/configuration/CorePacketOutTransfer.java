package de.atlasmc.core.node.io.protocol.configuration;

import de.atlasmc.core.node.io.protocol.common.CoreAbstractPacketTransfer;
import de.atlasmc.io.Packet;
import de.atlasmc.node.io.protocol.configuration.PacketOutTransfer;

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
