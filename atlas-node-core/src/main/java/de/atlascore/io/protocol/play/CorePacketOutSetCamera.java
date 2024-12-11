package de.atlascore.io.protocol.play;

import java.io.IOException;

import static de.atlasmc.io.protocol.ProtocolUtil.*;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutSetCamera;
import io.netty.buffer.ByteBuf;

public class CorePacketOutSetCamera implements PacketIO<PacketOutSetCamera> {

	@Override
	public void read(PacketOutSetCamera packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setEntityID(readVarInt(in));
	}

	@Override
	public void write(PacketOutSetCamera packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeVarInt(packet.getEntityID(), out);
	}

	@Override
	public PacketOutSetCamera createPacketData() {
		return new PacketOutSetCamera();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutSetCamera.class);
	}

}
