package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.IN_CLOSE_CONTAINER)
public class PacketInCloseContainer extends AbstractPacket implements PacketPlayIn {
	
	private int windowID;
	
	public int getWindowID() {
		return windowID;
	}
	
	public void setWindowID(int windowID) {
		this.windowID = windowID;
	}
	
	@Override
	public int getDefaultID() {
		return IN_CLOSE_CONTAINER;
	}

}
