package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketOutDestroyEntities;
import io.netty.buffer.ByteBuf;

public class CorePacketOutDestroyEntities extends AbstractPacket implements PacketOutDestroyEntities {

	private int[] entities; // for remove more than one
	private int entity; // for single remove
	
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
		if (entities != null) {
			writeVarInt(entities.length, out);
			for (int i : entities) {
				writeVarInt(i, out);
			}
		} else {
			writeVarInt(1, out);
			writeVarInt(entity, out);
		}
	}

	@Override
	public int[] getEntityIDs() {
		if (entities == null)
			entities = new int[] { entity };
		return entities;
	}

	@Override
	public void setEntityIDs(int[] ids) {
		entities = ids;
	}

	@Override
	public void setEntityID(int id) {
		entity = id;
	}

}
