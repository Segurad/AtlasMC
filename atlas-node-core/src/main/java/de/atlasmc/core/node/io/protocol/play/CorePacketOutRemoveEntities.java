package de.atlasmc.core.node.io.protocol.play;

import static de.atlasmc.io.PacketUtil.*;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.play.PacketOutRemoveEntities;
import io.netty.buffer.ByteBuf;

public class CorePacketOutRemoveEntities implements PacketIO<PacketOutRemoveEntities> {

	@Override
	public void read(PacketOutRemoveEntities packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		final int size = readVarInt(in);
		if (size == 0)
			return;
		int[] entities = new int[size];
		for (int i = 0; i < size; i++) {
			entities[i] = readVarInt(in);
		}
		packet.entityIDs = entities;
	}

	@Override
	public void write(PacketOutRemoveEntities packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		int[] entities = packet.entityIDs;
		if (entities != null) {
			writeVarInt(entities.length, out);
			for (int i : entities) {
				writeVarInt(i, out);
			}
		} else 
			writeVarInt(0, out);
	}

	@Override
	public PacketOutRemoveEntities createPacketData() {
		return new PacketOutRemoveEntities();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutRemoveEntities.class);
	}

}
