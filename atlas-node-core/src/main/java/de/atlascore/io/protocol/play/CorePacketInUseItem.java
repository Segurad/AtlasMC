package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlasmc.inventory.EquipmentSlot;
import static de.atlasmc.io.AbstractPacket.*;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketInUseItem;
import io.netty.buffer.ByteBuf;

public class CorePacketInUseItem implements PacketIO<PacketInUseItem> {

	@Override
	public void read(PacketInUseItem packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setHand(readVarInt(in) == 0 ? EquipmentSlot.MAIN_HAND : EquipmentSlot.OFF_HAND);
		packet.setSequence(readVarInt(in));
	}

	@Override
	public void write(PacketInUseItem packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeVarInt(packet.getHand() == EquipmentSlot.MAIN_HAND ? 0 : 1, out);
		writeVarInt(packet.getSequence(), out);
	}
	
	@Override
	public PacketInUseItem createPacketData() {
		return new PacketInUseItem();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketInUseItem.class);
	}

}
