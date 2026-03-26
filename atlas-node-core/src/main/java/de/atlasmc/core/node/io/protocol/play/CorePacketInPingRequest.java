package de.atlasmc.core.node.io.protocol.play;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketCodec;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.play.PacketInPingRequest;
import io.netty.buffer.ByteBuf;

public class CorePacketInPingRequest implements PacketCodec<PacketInPingRequest> {

	@Override
	public void deserialize(PacketInPingRequest packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.ping = in.readLong();
	}

	@Override
	public void serialize(PacketInPingRequest packet, ByteBuf out, ConnectionHandler con) throws IOException {
		out.writeLong(packet.ping);
	}

	@Override
	public PacketInPingRequest createPacketData() {
		return new PacketInPingRequest();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketInPingRequest.class);
	}

}
