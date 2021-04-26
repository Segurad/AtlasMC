package de.atlasmc.io.protocol.play;

import java.util.List;

import de.atlasmc.inventory.EquipmentSlot;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.io.Packet;
import de.atlasmc.util.Pair;

public interface PacketOutEntityEquipment extends Packet {
	
	public int getEntityID();
	public List<Pair<EquipmentSlot, ItemStack>> getSlots();
	
	@Override
	public default int getDefaultID() {
		return 0x47;
	}

}
