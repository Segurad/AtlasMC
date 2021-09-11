package de.atlasmc.io.protocol.play;

import de.atlasmc.event.inventory.InventoryType;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;

@DefaultPacketID(PacketPlay.OUT_OPEN_WINDOW)
public interface PacketOutOpenWindow extends PacketPlay, PacketOutbound {
	
	public int getWindowID();
	
	public InventoryType getWindowType();
	
	public String getTitle();
	
	public void setWindowID(int windowID);
	
	public void setWindowType(InventoryType type);
	
	public void setTitle(String title);
	
	@Override
	default int getDefaultID() {
		return OUT_OPEN_WINDOW;
	}
	

}
