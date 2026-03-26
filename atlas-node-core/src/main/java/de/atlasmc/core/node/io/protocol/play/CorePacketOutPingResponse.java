package de.atlasmc.core.node.io.protocol.play;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketCodec;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.play.PacketOutPingResponse;
import io.netty.buffer.ByteBuf;

public class CorePacketOutPingResponse implements PacketCodec<PacketOutPingResponse> {

	@Override
	public void deserialize(PacketOutPingResponse packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.payload = in.readLong();
	}

	@Override
	public void serialize(PacketOutPingResponse packet, ByteBuf out, ConnectionHandler con) throws IOException {
		out.writeLong(packet.payload);
	}

	@Override
	public PacketOutPingResponse createPacketData() {
		return new PacketOutPingResponse();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutPingResponse.class);
	}

}
