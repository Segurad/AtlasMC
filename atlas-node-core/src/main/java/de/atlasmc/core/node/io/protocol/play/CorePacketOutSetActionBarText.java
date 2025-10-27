package de.atlasmc.core.node.io.protocol.play;

import de.atlasmc.core.node.io.protocol.common.CoreAbstractPacketText;
import de.atlasmc.io.Packet;
import de.atlasmc.node.io.protocol.play.PacketOutSetActionBarText;

public class CorePacketOutSetActionBarText extends CoreAbstractPacketText<PacketOutSetActionBarText> {

	@Override
	public PacketOutSetActionBarText createPacketData() {
		return new PacketOutSetActionBarText();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutSetActionBarText.class);
	}

}
