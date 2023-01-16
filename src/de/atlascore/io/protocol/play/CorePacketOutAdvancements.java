package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.ConnectionHandler;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutAdvancements;
import io.netty.buffer.ByteBuf;

public class CorePacketOutAdvancements extends PacketIO<PacketOutAdvancements> {

	@Override
	public void read(PacketOutAdvancements packet, ByteBuf in, ConnectionHandler con) throws IOException {
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
	public void write(PacketOutAdvancements packet, ByteBuf out, ConnectionHandler con) throws IOException {
		throw new UnsupportedOperationException("Not implemented yet");
	}

	@Override
	public PacketOutAdvancements createPacketData() {
		return new PacketOutAdvancements();
	}



}
