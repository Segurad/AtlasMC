package de.atlasmc.io.protocol.play;

import de.atlasmc.inventory.InventoryType;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.OUT_OPEN_SCREEN)
public class PacketOutOpenScreen extends AbstractPacket implements PacketPlayOut {
	
	private int windowID;
	private InventoryType type;
	private String title;
	
	public int getWindowID() {
		return windowID;
	}

	public InventoryType getType() {
		return type;
	}

	public String getTitle() {
		return title;
	}

	public void setWindowID(int windowID) {
		this.windowID = windowID;
	}

	public void setType(InventoryType type) {
		this.type = type;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public int getDefaultID() {
		return OUT_OPEN_SCREEN;
	}
	

}
