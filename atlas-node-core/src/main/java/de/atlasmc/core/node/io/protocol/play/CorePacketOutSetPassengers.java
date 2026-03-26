package de.atlasmc.core.node.io.protocol.play;

import static de.atlasmc.io.PacketUtil.*;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketCodec;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.play.PacketOutSetPassengers;
import io.netty.buffer.ByteBuf;

public class CorePacketOutSetPassengers implements PacketCodec<PacketOutSetPassengers> {

	@Override
	public void deserialize(PacketOutSetPassengers packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.vehicleID = readVarInt(in);
		final int size = readVarInt(in);
		int[] passengers = new int[size];
		for (int i = 0; i < size; i++) {
			passengers[i] = readVarInt(in);
		}
		packet.passengers = passengers;
	}

	@Override
	public void serialize(PacketOutSetPassengers packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeVarInt(packet.vehicleID, out);
		writeVarInt(packet.passengers.length, out);
		for (int i : packet.passengers) {
			writeVarInt(i, out);
		}
	}

	@Override
	public PacketOutSetPassengers createPacketData() {
		return new PacketOutSetPassengers();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutSetPassengers.class);
	}

}
