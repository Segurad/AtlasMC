package de.atlasmc.core.node.io.protocol.play;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketCodec;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.play.PacketOutOpenSignEditor;
import io.netty.buffer.ByteBuf;

public class CorePacketOutOpenSignEditor implements PacketCodec<PacketOutOpenSignEditor> {
	
	@Override
	public void deserialize(PacketOutOpenSignEditor packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.position = in.readLong();
		packet.frontText = in.readBoolean();
	}

	@Override
	public void serialize(PacketOutOpenSignEditor packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		out.writeLong(packet.position);
		out.writeBoolean(packet.frontText);
	}

	@Override
	public PacketOutOpenSignEditor createPacketData() {
		return new PacketOutOpenSignEditor();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutOpenSignEditor.class);
	}

}
