package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.OUT_WINDOW_PROPERTY)
public class PacketOutWindowProperty extends AbstractPacket implements PacketPlayOut {

	private int windowID, property, value;
	
	public int getWindowID() {
		return windowID;
	}
	
	public void setWindowID(int windowID) {
		this.windowID = windowID;
	}
	
	public int getProperty() {
		return property;
	}
	
	public void setProperty(int property) {
		this.property = property;
	}
	
	public int getValue() {
		return value;
	}
	
	public void setValue(int value) {
		this.value = value;
	}
	
	@Override
	public int getDefaultID() {
		return OUT_WINDOW_PROPERTY;
	}
	
}
