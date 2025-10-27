package de.atlasmc.core.node.io.protocol.play;

import de.atlasmc.core.node.io.protocol.common.CoreAbstractPacketText;
import de.atlasmc.io.Packet;
import de.atlasmc.node.io.protocol.play.PacketOutSetTitleText;

public class CorePacketOutSetTitleText extends CoreAbstractPacketText<PacketOutSetTitleText> {

	@Override
	public PacketOutSetTitleText createPacketData() {
		return new PacketOutSetTitleText();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutSetTitleText.class);
	}

}
