package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.CoreAbstractHandler;
import de.atlasmc.inventory.EquipmentSlot;
import static de.atlasmc.io.AbstractPacket.*;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.protocol.play.PacketInEditBook;
import io.netty.buffer.ByteBuf;

public class CorePacketInEditBook extends CoreAbstractHandler<PacketInEditBook> {

	@Override
	public void read(PacketInEditBook packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.setBook(readSlot(in));
		packet.setSigning(in.readBoolean());
		packet.setHand(readVarInt(in) == 1 ? EquipmentSlot.OFF_HAND : EquipmentSlot.HAND);
	}

	@Override
	public void write(PacketInEditBook packet, ByteBuf out, ConnectionHandler con) throws IOException {
		writeSlot(packet.getBook(), out);
		out.writeBoolean(packet.isSigning());
		writeVarInt(packet.getHand() == EquipmentSlot.HAND ? 0 : 1, out);
	}
	
	@Override
	public PacketInEditBook createPacketData() {
		return new PacketInEditBook();
	}

}
