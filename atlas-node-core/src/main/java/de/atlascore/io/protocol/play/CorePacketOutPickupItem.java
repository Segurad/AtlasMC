package de.atlascore.io.protocol.play;

import java.io.IOException;

import static de.atlasmc.io.AbstractPacket.*;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutPickupItem;
import io.netty.buffer.ByteBuf;

public class CorePacketOutPickupItem implements PacketIO<PacketOutPickupItem> {

	@Override
	public void read(PacketOutPickupItem packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setCollectedID(readVarInt(in));
		packet.setCollectorID(readVarInt(in));
		packet.setPickupCount(readVarInt(in));
	}

	@Override
	public void write(PacketOutPickupItem packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeVarInt(packet.getCollectedID(), out);
		writeVarInt(packet.getCollectorID(), out);
		writeVarInt(packet.getPickupCount(), out);
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
