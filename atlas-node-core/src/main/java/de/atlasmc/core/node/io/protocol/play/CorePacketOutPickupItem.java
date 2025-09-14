package de.atlasmc.core.node.io.protocol.play;

import static de.atlasmc.io.PacketUtil.readVarInt;
import static de.atlasmc.io.PacketUtil.writeVarInt;

import java.io.IOException;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.node.io.protocol.play.PacketOutPickupItem;
import io.netty.buffer.ByteBuf;

public class CorePacketOutPickupItem implements PacketIO<PacketOutPickupItem> {

	@Override
	public void read(PacketOutPickupItem packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.collectedID = readVarInt(in);
		packet.collectorID = readVarInt(in);
		packet.pickupCount = readVarInt(in);
	}

	@Override
	public void write(PacketOutPickupItem packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeVarInt(packet.collectedID, out);
		writeVarInt(packet.collectorID, out);
		writeVarInt(packet.pickupCount, out);
	}

	@Override
	public PacketOutPickupItem createPacketData() {
		return new PacketOutPickupItem();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutPickupItem.class);
	}

}
