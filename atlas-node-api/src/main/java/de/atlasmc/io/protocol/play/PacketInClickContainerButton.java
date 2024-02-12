package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.IN_CLICK_CONTAINER_BUTTON)
public class PacketInClickContainerButton extends AbstractPacket implements PacketPlayIn {

	private int windowID;
	private int buttonID;
	
	public int getWindowID() {
		return windowID;
	}
	
	public void setWindowID(int windowID) {
		this.windowID = windowID;
	}
	
	public int getButtonID() {
		return buttonID;
	}
	
	public void setButtonID(int buttonID) {
		this.buttonID = buttonID;
	}
	
	@Override
	public int getDefaultID() {
		return IN_CLICK_CONTAINER_BUTTON;
	}
}
