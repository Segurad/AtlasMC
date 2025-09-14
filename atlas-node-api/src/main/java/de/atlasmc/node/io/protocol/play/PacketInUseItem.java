package de.atlasmc.node.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.node.inventory.EquipmentSlot;

@DefaultPacketID(packetID = PacketPlay.IN_USE_ITEM, definition = "use_item")
public class PacketInUseItem extends AbstractPacket implements PacketPlayIn {
	
	public EquipmentSlot hand;
	public int sequence;
	public float yaw;
	public float pitch;

	@Override
	public int getDefaultID() {
		return IN_USE_ITEM;
	}
	
}
