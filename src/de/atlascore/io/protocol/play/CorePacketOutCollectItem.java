package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketOutCollectItem;
import io.netty.buffer.ByteBuf;

public class CorePacketOutCollectItem extends AbstractPacket implements PacketOutCollectItem {

	private int collectedID, collectorID, count;
	
	public CorePacketOutCollectItem() {
		super(CoreProtocolAdapter.VERSION);
	}
	
	public CorePacketOutCollectItem(int collectedID, int collectorID, int count) {
		this();
		this.collectedID = collectedID;
		this.collectorID = collectorID;
		this.count = count;
	}

	@Override
	public void read(ByteBuf in) throws IOException {
		collectedID = readVarInt(in);
		collectorID = readVarInt(in);
		count = readVarInt(in);
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		writeVarInt(collectedID, out);
		writeVarInt(collectorID, out);
		writeVarInt(count, out);
	}

	@Override
	public int getCollectedID() {
		return collectedID;
	}

	@Override
	public int getCollectorID() {
		return collectorID;
	}

	@Override
	public int getPickupCount() {
		return count;
	}

}
