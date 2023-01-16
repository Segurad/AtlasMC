package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.ConnectionHandler;
import de.atlasmc.inventory.EquipmentSlot;
import static de.atlasmc.io.AbstractPacket.*;

import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutOpenBook;
import io.netty.buffer.ByteBuf;

public class CorePacketOutOpenBook extends PacketIO<PacketOutOpenBook> {

	@Override
	public void read(PacketOutOpenBook packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setHand(readVarInt(in) == 1 ? EquipmentSlot.OFF_HAND : EquipmentSlot.HAND);
	}

	@Override
	public void write(PacketOutOpenBook packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeVarInt(packet.getHand() == EquipmentSlot.HAND ? 0 : 1, out);
	}

	@Override
	public PacketOutOpenBook createPacketData() {
		return new PacketOutOpenBook();
	}

}
