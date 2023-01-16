package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.ConnectionHandler;
import de.atlasmc.inventory.EquipmentSlot;
import static de.atlasmc.io.AbstractPacket.*;

import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketInUseItem;
import io.netty.buffer.ByteBuf;

public class CorePacketInUseItem extends PacketIO<PacketInUseItem> {

	@Override
	public void read(PacketInUseItem packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setHand(readVarInt(in) == 0 ? EquipmentSlot.HAND : EquipmentSlot.OFF_HAND);
	}

	@Override
	public void write(PacketInUseItem packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeVarInt(packet.getHand() == EquipmentSlot.HAND ? 0 : 1, out);
	}
	
	@Override
	public PacketInUseItem createPacketData() {
		return new PacketInUseItem();
	}

}
