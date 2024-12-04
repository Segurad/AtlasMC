package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlasmc.inventory.EquipmentSlot;
import static de.atlasmc.io.AbstractPacket.*;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutOpenBook;
import io.netty.buffer.ByteBuf;

public class CorePacketOutOpenBook implements PacketIO<PacketOutOpenBook> {

	@Override
	public void read(PacketOutOpenBook packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setHand(readVarInt(in) == 1 ? EquipmentSlot.OFF_HAND : EquipmentSlot.MAIN_HAND);
	}

	@Override
	public void write(PacketOutOpenBook packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeVarInt(packet.getHand() == EquipmentSlot.MAIN_HAND ? 0 : 1, out);
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
