package de.atlasmc.core.node.io.protocol.play;

import static de.atlasmc.node.io.protocol.ProtocolUtil.*;

import java.io.IOException;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.node.io.protocol.play.PacketOutSetPlayerInventory;
import io.netty.buffer.ByteBuf;

public class CorePacketOutSetPlayerInventory implements PacketIO<PacketOutSetPlayerInventory> {

	@Override
	public void read(PacketOutSetPlayerInventory packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.slot = readVarInt(in);
		packet.item = readSlot(in);
	}

	@Override
	public void write(PacketOutSetPlayerInventory packet, ByteBuf out, ConnectionHandler con) throws IOException {
		writeVarInt(packet.slot, out);
		writeSlot(packet.item, out);
	}

	@Override
	public PacketOutSetPlayerInventory createPacketData() {
		return new PacketOutSetPlayerInventory();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutSetPlayerInventory.class);
	}

}
