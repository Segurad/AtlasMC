package de.atlasmc.core.node.io.protocol.play;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketCodec;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.play.PacketOutSetTitleAnimationTimes;
import io.netty.buffer.ByteBuf;

public class CorePacketOutSetTitleAnimationTimes implements PacketCodec<PacketOutSetTitleAnimationTimes> {

	@Override
	public void deserialize(PacketOutSetTitleAnimationTimes packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.fadeIn = in.readInt();
		packet.stay = in.readInt();
		packet.fadeOut = in.readInt();
	}

	@Override
	public void serialize(PacketOutSetTitleAnimationTimes packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		out.writeInt(packet.fadeIn);
		out.writeInt(packet.stay);
		out.writeInt(packet.fadeOut);
	}

	@Override
	public PacketOutSetTitleAnimationTimes createPacketData() {
		return new PacketOutSetTitleAnimationTimes();
	}
	
	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutSetTitleAnimationTimes.class);
	}

}
