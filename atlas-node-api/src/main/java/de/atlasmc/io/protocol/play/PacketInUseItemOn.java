package de.atlasmc.io.protocol.play;

import de.atlasmc.block.BlockFace;
import de.atlasmc.inventory.EquipmentSlot;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.IN_USE_ITEM_ON, definition = "use_item_on")
public class PacketInUseItemOn extends AbstractPacket implements PacketPlayIn {
	
	public EquipmentSlot hand;
	public long position;
	public BlockFace face;
	public float cursorPosX;
	public float cursorPosY;
	public float cursorPosZ;
	public boolean insideBlock;
	public int sequence;
	
	@Override
	public int getDefaultID() {
		return IN_USE_ITEM_ON;
	}

}
