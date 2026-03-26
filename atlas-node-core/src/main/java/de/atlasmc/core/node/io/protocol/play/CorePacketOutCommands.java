package de.atlasmc.core.node.io.protocol.play;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketCodec;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.play.PacketOutCommands;
import io.netty.buffer.ByteBuf;

public class CorePacketOutCommands implements PacketCodec<PacketOutCommands> {

	@Override
	public void deserialize(PacketOutCommands packet, ByteBuf in, ConnectionHandler con) throws IOException {
		// TODO implement
	}

	@Override
	public void serialize(PacketOutCommands packet, ByteBuf out, ConnectionHandler con) throws IOException {
		// TODO implement
	}

	@Override
	public PacketOutCommands createPacketData() {
		return new PacketOutCommands();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutCommands.class);
	}

	// TODO command implementation

}
