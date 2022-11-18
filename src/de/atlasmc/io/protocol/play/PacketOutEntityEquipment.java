package de.atlasmc.io.protocol.play;

import java.util.List;

import de.atlasmc.inventory.EquipmentSlot;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;
import de.atlasmc.util.Pair;

@DefaultPacketID(PacketPlay.OUT_ENTITY_EQUIPMENT)
public interface PacketOutEntityEquipment extends PacketPlay, PacketOutbound {
	
	public int getEntityID();
	
	public List<Pair<EquipmentSlot, ItemStack>> getSlots();
	
	public void setSlots(List<Pair<EquipmentSlot, ItemStack>> slots);
	
	public void setSlot(EquipmentSlot slot, ItemStack item);
	
	@Override
	public default int getDefaultID() {
		return 0x47;
	}

}
