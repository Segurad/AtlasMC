package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.IN_CLICK_CONTAINER_BUTTON, definition = "click_button")
public class PacketInClickContainerButton extends AbstractPacket implements PacketPlayIn {

	public int windowID;
	public int buttonID;
	
	@Override
	public int getDefaultID() {
		return IN_CLICK_CONTAINER_BUTTON;
	}
}
