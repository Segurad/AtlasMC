package de.atlascore.io.protocol.play;

import java.io.IOException;

import static de.atlasmc.io.AbstractPacket.*;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutSetPassengers;
import io.netty.buffer.ByteBuf;

public class CorePacketOutSetPassengers implements PacketIO<PacketOutSetPassengers> {

	@Override
	public void read(PacketOutSetPassengers packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setVehicleID(readVarInt(in));
		final int size = readVarInt(in);
		int[] passengers = new int[size];
		for (int i = 0; i < size; i++) {
			passengers[i] = readVarInt(in);
		}
		packet.setPassengers(passengers);
	}

	@Override
	public void write(PacketOutSetPassengers packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeVarInt(packet.getVehicleID(), out);
		writeVarInt(packet.getPassengers().length, out);
		for (int i : packet.getPassengers()) {
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
