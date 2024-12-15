package de.atlascore.io.protocol.play;

import de.atlascore.io.protocol.common.CoreAbstractPacketTransfer;
import de.atlasmc.io.Packet;
import de.atlasmc.io.protocol.play.PacketOutTransfer;

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
