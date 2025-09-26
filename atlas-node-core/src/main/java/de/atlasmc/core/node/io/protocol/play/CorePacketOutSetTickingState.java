package de.atlasmc.core.node.io.protocol.play;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.play.PacketOutSetTickingState;
import io.netty.buffer.ByteBuf;

public class CorePacketOutSetTickingState implements PacketIO<PacketOutSetTickingState> {

	@Override
	public void read(PacketOutSetTickingState packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.tickRate = in.readFloat();
		packet.frozen = in.readBoolean();
	}

	@Override
	public void write(PacketOutSetTickingState packet, ByteBuf out, ConnectionHandler con) throws IOException {
		out.writeFloat(packet.tickRate);
		out.writeBoolean(packet.frozen);
	}

	@Override
	public PacketOutSetTickingState createPacketData() {
		return new PacketOutSetTickingState();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutSetTickingState.class);
	}

}
