package de.atlasmc.core.node.io.protocol.play;

import java.io.IOException;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.node.io.protocol.play.PacketOutUpdateAdvancements;
import io.netty.buffer.ByteBuf;

public class CorePacketOutUpdateAdvancements implements PacketIO<PacketOutUpdateAdvancements> {

	@Override
	public void read(PacketOutUpdateAdvancements packet, ByteBuf in, ConnectionHandler con) throws IOException {
		throw new UnsupportedOperationException("Not implemented yet!");
//		packet.setReset(in.readBoolean());
//		int size = readVarInt(in); // Size of advancements
//		if (size > 0) {
//			Map<String, Advancement> advancements = new HashMap<>(size);
//			for (int i = 0; i < size; i++) {
//				String key = readString(in);
//				// TODO implement advancements
//			}
//		}
	}

	@Override
	public void write(PacketOutUpdateAdvancements packet, ByteBuf out, ConnectionHandler con) throws IOException {
		throw new UnsupportedOperationException("Not implemented yet");
	}

	@Override
	public PacketOutUpdateAdvancements createPacketData() {
		return new PacketOutUpdateAdvancements();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutUpdateAdvancements.class);
	}



}
