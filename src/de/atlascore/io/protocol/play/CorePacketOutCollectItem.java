package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.CoreAbstractHandler;
import static de.atlasmc.io.AbstractPacket.*;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.protocol.play.PacketOutCollectItem;
import io.netty.buffer.ByteBuf;

public class CorePacketOutCollectItem extends CoreAbstractHandler<PacketOutCollectItem> {

	@Override
	public void read(PacketOutCollectItem packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setCollectedID(readVarInt(in));
		packet.setCollectorID(readVarInt(in));
		packet.setPickupCount(readVarInt(in));
	}

	@Override
	public void write(PacketOutCollectItem packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeVarInt(packet.getCollectedID(), out);
		writeVarInt(packet.getCollectorID(), out);
		writeVarInt(packet.getPickupCount(), out);
	}

	@Override
	public PacketOutCollectItem createPacketData() {
		return new PacketOutCollectItem();
	}

}
