package de.atlascore.io.protocol.play;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static de.atlasmc.io.AbstractPacket.*;

import de.atlascore.io.ConnectionHandler;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutTags;
import io.netty.buffer.ByteBuf;

public class CorePacketOutTags extends PacketIO<PacketOutTags> {

	@Override
	public void read(PacketOutTags packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setBlockTags(readMap(in));
		packet.setItemTags(readMap(in));
		packet.setFluidTags(readMap(in));
		packet.setEntityTags(readMap(in));
	}

	@Override
	public void write(PacketOutTags packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeMap(packet.getBlockTags(), out);
		writeMap(packet.getItemTags(), out);
		writeMap(packet.getFluidTags(), out);
		writeMap(packet.getEntityTags(), out);
	}
	
	private Map<String, int[]> readMap(ByteBuf in) {
		final int length = readVarInt(in);
		HashMap<String, int[]> map = new HashMap<>(length);
		for (int i = 0; i < length; i++) {
			String name = readString(in);
			final int count = readVarInt(in);
			final int[] entries = new int[count];
			for (int j = 0; j < count; j++) {
				entries[j] = readVarInt(in);
			}
			map.put(name, entries);
		}
		return map;
	}
	
	private void writeMap(Map<String, int[]> map, ByteBuf out) {
		writeVarInt(map.size(), out);
		map.forEach((k, v) -> {
			writeString(k, out);
			writeVarInt(v.length, out);
			for (int i : v) {
				writeVarInt(i, out);
			}
		});
	}
	
	@Override
	public PacketOutTags createPacketData() {
		return new PacketOutTags();
	}
	
}
