package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.OUT_CLOSE_CONTAINER)
public class PacketOutCloseContainer extends AbstractPacket implements PacketPlayOut {
	
	private int windowID;
	
	public int getWindowID() {
		return windowID;
	}
	
	public void setWindowID(int windowID) {
		this.windowID = windowID;
	}
	
	@Override
	public int getDefaultID() {
		return OUT_CLOSE_CONTAINER;
	}

}
