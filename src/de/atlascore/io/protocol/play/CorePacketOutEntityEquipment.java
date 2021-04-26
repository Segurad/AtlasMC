package de.atlascore.io.protocol.play;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.inventory.EquipmentSlot;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketOutEntityEquipment;
import de.atlasmc.util.Pair;
import io.netty.buffer.ByteBuf;

public class CorePacketOutEntityEquipment extends AbstractPacket implements PacketOutEntityEquipment {
	
	private int entityID;
	private List<Pair<EquipmentSlot, ItemStack>> slots;
	
	public CorePacketOutEntityEquipment() {
		super(CoreProtocolAdapter.VERSION);
	}
	
	public CorePacketOutEntityEquipment(int entityID, List<Pair<EquipmentSlot, ItemStack>> slots) {
		this();
		this.slots = slots;
	}

	@Override
	public void read(ByteBuf in) throws IOException {
		entityID = readVarInt(in);
		slots = new ArrayList<Pair<EquipmentSlot,ItemStack>>();
		boolean next;
		do {
			int raw = in.readByte();
			next = (raw & 0x80) == 0x80;
			EquipmentSlot slot = EquipmentSlot.getByID(raw & 0x7F);
			ItemStack item = readSlot(in);
			slots.add(Pair.of(slot, item));
		} while (next);
	}
	
	@Override
	public void write(ByteBuf out) throws IOException {
		writeVarInt(entityID, out);
		final int size = slots.size();
		for (int i = 0; i < size; i++) {
			Pair<EquipmentSlot, ItemStack> pair = slots.get(i);
			int slot = pair.getValue1().getID();
			if (i+1 < size) slot += 0x80;
			out.writeInt(slot);
			writeSlot(pair.getValue2(), out);
		}
	}
	
	@Override
	public int getEntityID() {
		return entityID;
	}

	@Override
	public List<Pair<EquipmentSlot, ItemStack>> getSlots() {
		return slots;
	}

}
