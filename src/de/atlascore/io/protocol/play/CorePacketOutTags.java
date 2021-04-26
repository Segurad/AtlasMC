package de.atlascore.io.protocol.play;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketOutTags;
import io.netty.buffer.ByteBuf;

public class CorePacketOutTags extends AbstractPacket implements PacketOutTags {

	private Map<String, int[]> blocks, items, entities, fluids;
	
	public CorePacketOutTags() {
		super(CoreProtocolAdapter.VERSION);
	}
	
	public CorePacketOutTags(Map<String, int[]> blocks, Map<String, int[]> items, Map<String, int[]> entities, Map<String, int[]> fluids) {
		this();
		this.blocks = blocks;
		this.items = items;
		this.entities = entities;
		this.fluids = fluids;
	}

	@Override
	public void read(ByteBuf in) throws IOException {
		blocks = readMap(in);
		items = readMap(in);
		fluids = readMap(in);
		entities = readMap(in);
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		writeMap(blocks, out);
		writeMap(items, out);
		writeMap(fluids, out);
		writeMap(entities, out);
	}
	
	private Map<String, int[]> readMap(ByteBuf in) {
		final int length = readVarInt(in);
		HashMap<String, int[]> map = new HashMap<String, int[]>(length);
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
	public Map<String, int[]> getBlockTags() {
		return blocks;
	}

	@Override
	public Map<String, int[]> getItemTags() {
		return items;
	}

	@Override
	public Map<String, int[]> getEntityTags() {
		return entities;
	}

	@Override
	public Map<String, int[]> getFluidTags() {
		return fluids;
	}

}
