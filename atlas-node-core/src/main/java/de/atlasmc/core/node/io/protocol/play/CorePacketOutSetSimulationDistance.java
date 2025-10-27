package de.atlasmc.core.node.io.protocol.play;

import static de.atlasmc.io.PacketUtil.*;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.play.PacketOutSetSimulationDistance;
import io.netty.buffer.ByteBuf;

public class CorePacketOutSetSimulationDistance implements PacketIO<PacketOutSetSimulationDistance> {

	@Override
	public void read(PacketOutSetSimulationDistance packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.distance = readVarInt(in);
	}

	@Override
	public void write(PacketOutSetSimulationDistance packet, ByteBuf out, ConnectionHandler con) throws IOException {
		writeVarInt(packet.distance, out);
	}

	@Override
	public PacketOutSetSimulationDistance createPacketData() {
		return new PacketOutSetSimulationDistance();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutSetSimulationDistance.class);
	}

}
