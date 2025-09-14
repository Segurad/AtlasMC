package de.atlasmc.core.node.io.protocol.play;

import static de.atlasmc.node.io.protocol.ProtocolUtil.*;

import java.io.IOException;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.node.inventory.EquipmentSlot;
import de.atlasmc.node.io.protocol.play.PacketOutOpenBook;
import io.netty.buffer.ByteBuf;

public class CorePacketOutOpenBook implements PacketIO<PacketOutOpenBook> {

	@Override
	public void read(PacketOutOpenBook packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.hand = readVarInt(in) == 1 ? EquipmentSlot.OFF_HAND : EquipmentSlot.MAIN_HAND;
	}

	@Override
	public void write(PacketOutOpenBook packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeVarInt(packet.hand == EquipmentSlot.MAIN_HAND ? 0 : 1, out);
	}

	@Override
	public PacketOutOpenBook createPacketData() {
		return new PacketOutOpenBook();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutOpenBook.class);
	}

}
