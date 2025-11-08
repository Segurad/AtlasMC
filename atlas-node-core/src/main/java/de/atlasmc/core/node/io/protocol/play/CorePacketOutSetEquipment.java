package de.atlasmc.core.node.io.protocol.play;

import static de.atlasmc.io.PacketUtil.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.inventory.EquipmentSlot;
import de.atlasmc.node.inventory.ItemStack;
import de.atlasmc.node.io.protocol.play.PacketOutSetEquipment;
import de.atlasmc.util.Pair;
import de.atlasmc.util.codec.CodecContext;
import de.atlasmc.util.enums.EnumUtil;
import io.netty.buffer.ByteBuf;

public class CorePacketOutSetEquipment implements PacketIO<PacketOutSetEquipment> {

	@Override
	public void read(PacketOutSetEquipment packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.entityID = readVarInt(in);
		List<Pair<EquipmentSlot, ItemStack>> slots = new ArrayList<>();
		final CodecContext context = handler.getCodecContext();
		boolean next;
		do {
			int raw = in.readByte();
			next = (raw & 0x80) != 0x00;
			EquipmentSlot slot = EnumUtil.getByID(EquipmentSlot.class, raw & 0x7F);
			ItemStack item = ItemStack.STREAM_CODEC.deserialize(in, context);
			slots.add(Pair.of(slot, item));
		} while (next);
		packet.slots = slots;
	}
	
	@Override
	public void write(PacketOutSetEquipment packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeVarInt(packet.entityID, out);
		List<Pair<EquipmentSlot, ItemStack>> slots = packet.slots;
		final int size = slots.size();
		final CodecContext context = handler.getCodecContext();
		for (int i = 0; i < size; i++) {
			Pair<EquipmentSlot, ItemStack> pair = slots.get(i);
			int slot = pair.value1().getID();
			if (i+1 < size) 
				slot |= 0x80;
			out.writeInt(slot);
			ItemStack.STREAM_CODEC.serialize(pair.value2(), out, context);
		}
	}

	@Override
	public PacketOutSetEquipment createPacketData() {
		return new PacketOutSetEquipment();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutSetEquipment.class);
	}

}
