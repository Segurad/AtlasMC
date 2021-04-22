package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketOutDestroyEntities;
import io.netty.buffer.ByteBuf;

public class CorePacketOutDestroyEntities extends AbstractPacket implements PacketOutDestroyEntities {

	private int[] entities;
	
	public CorePacketOutDestroyEntities() {
		super(CoreProtocolAdapter.VERSION);
	}
	
	public CorePacketOutDestroyEntities(int[] entities) {
		this();
		this.entities = entities;
	}

	@Override
	public void read(ByteBuf in) throws IOException {
		final int size = readVarInt(in);
		entities = new int[size];
		for (int i = 0; i < size; i++) {
			entities[i] = readVarInt(in);
		}
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		writeVarInt(entities.length, out);
		for (int i : entities) {
			writeVarInt(i, out);
		}
	}

	@Override
	public int[] getEntityIDs() {
		return entities;
	}

}
