package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;

@DefaultPacketID(PacketPlay.OUT_WINDOW_PROPERTY)
public interface PacketOutWindowProperty extends PacketPlay, PacketOutbound {

	public byte getWindowID();
	
	public int getProperty();
	
	public int getValue();
	
	public void setWindowID(int windowID);
	
	public void setProperty(int property);
	
	public void setValue(int value);
	
	@Override
	default int getDefaultID() {
		return OUT_WINDOW_PROPERTY;
	}
	
}
