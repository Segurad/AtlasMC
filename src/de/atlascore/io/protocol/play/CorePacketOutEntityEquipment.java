package de.atlascore.io.protocol.play;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.atlascore.io.ConnectionHandler;
import de.atlasmc.inventory.EquipmentSlot;
import de.atlasmc.inventory.ItemStack;
import static de.atlasmc.io.AbstractPacket.*;

import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutEntityEquipment;
import de.atlasmc.util.Pair;
import io.netty.buffer.ByteBuf;

public class CorePacketOutEntityEquipment extends PacketIO<PacketOutEntityEquipment> {

	@Override
	public void read(PacketOutEntityEquipment packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setEntityID(readVarInt(in));
		List<Pair<EquipmentSlot, ItemStack>> slots = new ArrayList<>();
		boolean next;
		do {
			int raw = in.readByte();
			next = (raw & 0x80) == 0x80;
			EquipmentSlot slot = EquipmentSlot.getByID(raw & 0x7F);
			ItemStack item = readSlot(in);
			slots.add(new Pair<>(slot, item));
		} while (next);
		packet.setSlots(slots);
	}
	
	@Override
	public void write(PacketOutEntityEquipment packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeVarInt(packet.getEntityID(), out);
		List<Pair<EquipmentSlot, ItemStack>> slots = packet.getSlots();
		final int size = slots.size();
		for (int i = 0; i < size; i++) {
			Pair<EquipmentSlot, ItemStack> pair = slots.get(i);
			int slot = pair.getValue1().getID();
			if (i+1 < size) 
				slot |= 0x80;
			out.writeInt(slot);
			writeSlot(pair.getValue2(), out);
		}
	}

	@Override
	public PacketOutEntityEquipment createPacketData() {
		return new PacketOutEntityEquipment();
	}

}
