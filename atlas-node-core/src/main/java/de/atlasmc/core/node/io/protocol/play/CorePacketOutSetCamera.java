package de.atlasmc.core.node.io.protocol.play;

import static de.atlasmc.io.PacketUtil.*;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketCodec;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.play.PacketOutSetCamera;
import io.netty.buffer.ByteBuf;

public class CorePacketOutSetCamera implements PacketCodec<PacketOutSetCamera> {

	@Override
	public void deserialize(PacketOutSetCamera packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setEntityID(readVarInt(in));
	}

	@Override
	public void serialize(PacketOutSetCamera packet, ByteBuf out, ConnectionHandler handler) throws IOException {
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
