package de.atlasmc.node.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.node.inventory.EquipmentSlot;

@DefaultPacketID(packetID = PacketPlay.IN_INTERACT, definition = "interact")
public class PacketInInteract extends AbstractPacket implements PacketPlayIn {
	
	public int entityID;
	public int type;
	public EquipmentSlot hand;
	public float x;
	public float y;
	public float z;
	public boolean sneaking;
	
	@Override
	public int getDefaultID() {
		return IN_INTERACT;
	}
	
}
