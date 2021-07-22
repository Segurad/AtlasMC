package de.atlasmc.io.protocol.play;

import de.atlasmc.inventory.EquipmentSlot;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;

@DefaultPacketID(PacketPlay.OUT_OPEN_BOOK)
public interface PacketOutOpenBook extends PacketPlay, PacketOutbound {
	
	public EquipmentSlot getHand();
	
	@Override
	public default int getDefaultID() {
		return OUT_OPEN_BOOK;
	}

}
