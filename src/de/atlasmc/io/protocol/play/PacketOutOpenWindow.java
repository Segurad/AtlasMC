package de.atlasmc.io.protocol.play;

import de.atlasmc.event.inventory.InventoryType;
import de.atlasmc.io.Packet;

public interface PacketOutOpenWindow extends Packet {
	
	public int getWindowID();
	public InventoryType getWindowType();
	public String getTitle();
	
	@Override
	default int getDefaultID() {
		return 0x2D;
	}

}
