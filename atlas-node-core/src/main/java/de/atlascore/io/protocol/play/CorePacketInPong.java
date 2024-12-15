package de.atlascore.io.protocol.play;

import de.atlascore.io.protocol.common.CoreAbstractPacketPing;
import de.atlasmc.io.Packet;
import de.atlasmc.io.protocol.play.PacketInPong;

public class CorePacketInPong extends CoreAbstractPacketPing<PacketInPong> {

	@Override
	public PacketInPong createPacketData() {
		return new PacketInPong();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketInPong.class);
	}

}
