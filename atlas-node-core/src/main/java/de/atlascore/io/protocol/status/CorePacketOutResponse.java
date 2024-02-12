package de.atlascore.io.protocol.status;

import java.io.IOException;

import static de.atlasmc.io.AbstractPacket.*;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.status.PacketOutResponse;
import io.netty.buffer.ByteBuf;

public class CorePacketOutResponse implements PacketIO<PacketOutResponse> {

	@Override
	public void read(PacketOutResponse packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setResponse(readString(in));
	}

	@Override
	public void write(PacketOutResponse packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeString(packet.getResponse(), out);
	}
	
	@Override
	public PacketOutResponse createPacketData() {
		return new PacketOutResponse();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutResponse.class);
	}

}
