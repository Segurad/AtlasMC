package de.atlasmc.io.protocol.play;

import de.atlasmc.inventory.EquipmentSlot;
import de.atlasmc.io.Packet;

public interface PacketOutOpenBook extends Packet {
	
	public EquipmentSlot getHand();
	
	@Override
	public default int getDefaultID() {
		return 0x2C;
	}

}
