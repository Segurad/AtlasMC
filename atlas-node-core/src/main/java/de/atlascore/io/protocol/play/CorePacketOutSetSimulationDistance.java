package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutSetSimulationDistance;
import io.netty.buffer.ByteBuf;
import static de.atlasmc.io.protocol.ProtocolUtil.*;

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
