package de.atlasmc.core.node.io.protocol.play;

import de.atlasmc.core.node.io.protocol.common.CoreAbstractPacketText;
import de.atlasmc.io.Packet;
import de.atlasmc.node.io.protocol.play.PacketOutSetSubtitleText;

public class CorePacketOutSetSubtitleText extends CoreAbstractPacketText<PacketOutSetSubtitleText> {

	@Override
	public PacketOutSetSubtitleText createPacketData() {
		return new PacketOutSetSubtitleText();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutSetSubtitleText.class);
	}

}
