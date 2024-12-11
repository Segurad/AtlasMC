package de.atlasmc.io.protocol.play;

import de.atlasmc.chat.Chat;
import de.atlasmc.inventory.InventoryType;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.OUT_OPEN_SCREEN, definition = "open_screen")
public class PacketOutOpenScreen extends AbstractPacket implements PacketPlayOut {
	
	public int windowID;
	public InventoryType type;
	public Chat title;

	@Override
	public int getDefaultID() {
		return OUT_OPEN_SCREEN;
	}
	

}
