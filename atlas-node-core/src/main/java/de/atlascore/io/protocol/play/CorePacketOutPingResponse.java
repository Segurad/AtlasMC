package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutPingResponse;
import io.netty.buffer.ByteBuf;

public class CorePacketOutPingResponse implements PacketIO<PacketOutPingResponse> {

	@Override
	public void read(PacketOutPingResponse packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.payload = in.readLong();
	}

	@Override
	public void write(PacketOutPingResponse packet, ByteBuf out, ConnectionHandler con) throws IOException {
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
