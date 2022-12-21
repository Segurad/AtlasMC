package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.CoreAbstractHandler;
import static de.atlasmc.io.AbstractPacket.*;
import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.protocol.play.PacketOutDestroyEntities;
import io.netty.buffer.ByteBuf;

public class CorePacketOutDestroyEntities extends CoreAbstractHandler<PacketOutDestroyEntities> {

	@Override
	public void read(PacketOutDestroyEntities packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		final int size = readVarInt(in);
		if (size == 0)
			return;
		int[] entities = new int[size];
		for (int i = 0; i < size; i++) {
			entities[i] = readVarInt(in);
		}
		packet.setEntityIDs(entities);
	}

	@Override
	public void write(PacketOutDestroyEntities packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		int[] entities = packet.getEntityIDs();
		if (entities != null) {
			writeVarInt(entities.length, out);
			for (int i : entities) {
				writeVarInt(i, out);
			}
		} else 
			writeVarInt(0, out);
	}

	@Override
	public PacketOutDestroyEntities createPacketData() {
		return new PacketOutDestroyEntities();
	}

}
