package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.IN_NAME_ITEM)
public class  PacketInNameItem extends AbstractPacket implements PacketPlayIn {
	
	private String itemName;
	
	public String getItemName() {
		return itemName;
	}
	
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	@Override
	public int getDefaultID() {
		return IN_NAME_ITEM;
	}
	
}
