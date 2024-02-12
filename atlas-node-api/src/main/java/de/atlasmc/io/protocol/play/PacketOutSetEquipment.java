package de.atlasmc.io.protocol.play;

import java.util.ArrayList;
import java.util.List;

import de.atlasmc.inventory.EquipmentSlot;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.util.Pair;

@DefaultPacketID(PacketPlay.OUT_SET_EQUIPMENT)
public class PacketOutSetEquipment extends AbstractPacket implements PacketPlayOut {
	
	private int entityID;
	private List<Pair<EquipmentSlot, ItemStack>> slots;
	
	public int getEntityID() {
		return entityID;
	}
	
	public void setEntityID(int entityID) {
		this.entityID = entityID;
	}
	
	public List<Pair<EquipmentSlot, ItemStack>> getSlots() {
		return slots;
	}
	
	public void setSlots(List<Pair<EquipmentSlot, ItemStack>> slots) {
		this.slots = slots;
	}
	
	public void setSlot(EquipmentSlot slot, ItemStack item) {
		slots = new ArrayList<>(1);
		slots.add(Pair.of(slot, item));
	}
	
	@Override
	public int getDefaultID() {
		return OUT_SET_EQUIPMENT;
	}

}
