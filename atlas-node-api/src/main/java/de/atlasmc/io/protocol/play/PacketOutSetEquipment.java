package de.atlasmc.io.protocol.play;

import java.util.ArrayList;
import java.util.List;

import de.atlasmc.inventory.EquipmentSlot;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.util.Pair;

@DefaultPacketID(packetID = PacketPlay.OUT_SET_EQUIPMENT, definition = "set_equipment")
public class PacketOutSetEquipment extends AbstractPacket implements PacketPlayOut {
	
	public int entityID;
	public List<Pair<EquipmentSlot, ItemStack>> slots;
	
	public void setSlot(EquipmentSlot slot, ItemStack item) {
		slots = new ArrayList<>(1);
		slots.add(Pair.of(slot, item));
	}
	
	@Override
	public int getDefaultID() {
		return OUT_SET_EQUIPMENT;
	}

}
